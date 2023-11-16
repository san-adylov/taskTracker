package com.app.tasktracker.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.app.tasktracker.dto.request.LabelRequest;
import com.app.tasktracker.dto.response.LabelResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.exceptions.AlreadyExistException;
import com.app.tasktracker.exceptions.NotFoundException;
import com.app.tasktracker.models.Card;
import com.app.tasktracker.models.Label;
import com.app.tasktracker.repositories.CardRepository;
import com.app.tasktracker.repositories.LabelRepository;
import com.app.tasktracker.repositories.customRepository.CustomLabelRepository;
import com.app.tasktracker.services.LabelService;


import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;
    private final CardRepository cardRepository;
    private final CustomLabelRepository labelJdbcTemplateService;

    @Override
    public List<LabelResponse> getAllLabels() {
        return labelJdbcTemplateService.getAllLabels();
    }

    @Override
    public SimpleResponse saveLabels(LabelRequest labelRequest) {
        Label label = new Label(labelRequest.description(), labelRequest.color());
        labelRepository.save(label);
        log.info(String.format("Label with name : %s  successfully saved!", label.getLabelName()));
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Label with name : %s  successfully saved!", label.getLabelName()))
                .build();
    }

    @Override
    public SimpleResponse addLabelsToCard(Long cardId, Long labelId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> {
            log.error(String.format("Card with id : %s doesn't exist!", cardId));
            return new NotFoundException(String.format("Card with id : %s doesn't exist!", cardId));
        });
        Label label = labelRepository.findById(labelId).orElseThrow(() -> {
            log.error(String.format("Label with id : %s doesn't exist!", labelId));
            return new NotFoundException(String.format("Label with id : %s doesn't exist!", labelId));
        });

//        List<String> allowedColors = Arrays.asList("Green", "Blue", "Red", "Yellow");
//
//        if (!allowedColors.contains(label.getColor())) {
//            throw new BadCredentialException(String.format("Label color '%s' is not allowed.", label.getColor()));
//        }

        for (Label l : card.getLabels()) {
            if (l.getLabelName().equalsIgnoreCase(label.getLabelName()) && l.getColor().equalsIgnoreCase(label.getColor())) {
                throw new AlreadyExistException(String.format("Label with name '%s' and color '%s' already exists on the card!", label.getLabelName(), label.getColor()));
            }
        }

        if (card.getLabels().stream().anyMatch(l -> l.getId().equals(labelId))) {
            throw new AlreadyExistException(String.format("Label with id '%s' already exists on the card!", labelId));
        }

        label.getCards().add(card);
        card.getLabels().add(label);
        cardRepository.save(card);
        labelRepository.save(label);

        log.info(String.format("Label with name: %s successfully added to card with name: %s", label.getLabelName(), card.getTitle()));
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Label with name: %s successfully added to card with name: %s", label.getLabelName(), card.getTitle()))
                .build();
    }


    @Override
    public LabelResponse getLabelById(Long labelId) {
        return labelJdbcTemplateService.getLabelById(labelId);
    }

    @Override
    public SimpleResponse updateLabelDeleteById(Long labelId, LabelRequest labelRequest) {
        Label label = labelRepository.findById(labelId).orElseThrow(() -> {
            log.error(String.format("Label with id: %s doesn't exist !", labelId));
            return new NotFoundException(String.format("Label with id: %s doesn't exist !", labelId));
        });
        log.info(String.format("Label with id : %s  successfully updated!", label.getId()));
        label.setLabelName(labelRequest.description());
        label.setColor(labelRequest.color());
        labelRepository.save(label);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Label with id : %s  successfully updated!", label.getId()))
                .build();
    }

    @Override
    public SimpleResponse deleteFromCardByIdLables(Long cardId, Long labelId) {
        Card card = cardRepository.findById(cardId).orElseThrow(()->{
            log.error("Card with id: " + cardId + "not found");
            return new NotFoundException("Card with id: " + cardId + "not found");
        });
        Label label = labelRepository.findById(labelId).orElseThrow(()->{
            log.error("Label with id: " + cardId + "not found");
            return new NotFoundException("Label with id: " + cardId + "not found");
        });
        card.getLabels().remove(label);
        label.getCards().remove(card);
        cardRepository.save(card);
        labelRepository.save(label);

        return new SimpleResponse("Метка успешно удалена из карты.", HttpStatus.OK);
    }


    @Override
    public List<LabelResponse> getAllLabelsByCardId(Long cardId) {
        cardRepository.findById(cardId).orElseThrow(() -> {
            log.error(String.format("Card with id: %s doesn't exist !", cardId));
            return new NotFoundException(String.format("Card with id: %s doesn't exist !", cardId));
        });
        return labelJdbcTemplateService.getAllLabelsByCardId(cardId);
    }
}
