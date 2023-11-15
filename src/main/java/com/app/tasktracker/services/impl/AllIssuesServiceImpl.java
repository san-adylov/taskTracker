package com.app.tasktracker.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.app.tasktracker.dto.response.AllIssuesResponse;
import com.app.tasktracker.repositories.CheckListRepository;
import com.app.tasktracker.repositories.LabelRepository;
import com.app.tasktracker.repositories.UserRepository;
import com.app.tasktracker.repositories.customRepository.CustomAllIssuesRepository;
import com.app.tasktracker.services.AllIssuesService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AllIssuesServiceImpl implements AllIssuesService {

    private final CustomAllIssuesRepository customAllIssuesRepository;
    private final LabelRepository labelRepository;
    private final UserRepository userRepository;
    private final CheckListRepository checkListRepository;

    @Override
    public List<AllIssuesResponse> filterIssues(Long workSpaceId, LocalDate from, LocalDate to, List<Long> labelIds, List<Long> assigneeMemberIds) {

        List<AllIssuesResponse> allIssuesResponses = customAllIssuesRepository.filterIssues(workSpaceId, from, to, labelIds, assigneeMemberIds);

        for (AllIssuesResponse allIssuesResponse : allIssuesResponses) {
            allIssuesResponse.setIsChecked(checkListRepository.doesCheckListExistForCard(allIssuesResponse.getCardId()));
            allIssuesResponse.setLabelResponses(labelRepository.findAllByCardId(allIssuesResponse.getCardId()));
            allIssuesResponse.setAssignee(userRepository.findAllParticipantByCardId(allIssuesResponse.getCardId()));
        }

        return allIssuesResponses;
    }
}