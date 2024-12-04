package com.equaled.service.impl;

import com.equaled.entity.*;
import com.equaled.eserve.common.exception.IncorrectArgumentException;
import com.equaled.repository.IPassageAnswersRepository;
import com.equaled.repository.IPassageQuestionsRepository;
import com.equaled.repository.IPassageRepository;
import com.equaled.repository.IUserRepository;
import com.equaled.service.IPassageV2;
import com.equaled.to.CommonV2Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PassageV2Impl implements IPassageV2 {

    IPassageRepository passageRepository;
    IPassageQuestionsRepository passageQuestionsRepository;
    IPassageAnswersRepository passageAnswersRepository;
    IUserRepository userRepository;


    @Override
    public Map<String, List<CommonV2Response>> createPassage(Map<String,String> request){
        Passage passage = new Passage();
        passage.setSid(BaseEntity.generateByteUuid());
        passage.setTitle(request.getOrDefault("Title",""));
        passage.setContent(request.getOrDefault("Content",""));
        Optional<Users> author = Optional.ofNullable(request.get("Author")).map(Integer::parseInt)
                .flatMap(userRepository::findById);
        passage.setAuthor(author.orElseThrow(() -> new IncorrectArgumentException("Invalid User Id")));

        log.trace("Submitting Passage");
        Passage passage1 = passageRepository.save(passage);
        log.debug("Passage created : {}", passage1.getStringSid());


        CommonV2Response commonV2Response = new CommonV2Response();
        commonV2Response.setId(passage1.getStringSid());
        commonV2Response.putField("Title", passage1.getTitle());
        commonV2Response.putField("Content", passage1.getContent());
        commonV2Response.putField("Author", String.valueOf(passage1.getAuthor().getId()));
        commonV2Response.putField("PublicationDate", passage1.getPublicationDate().toString());
        return generateResponse(Collections.singletonList(commonV2Response));
    }

    @Override
    public Map<String, List<CommonV2Response>> createPassageQuestions(Map<String, String> request){
        PassageQuestions passageQuestions = new PassageQuestions();
        passageQuestions.setSid(BaseEntity.generateByteUuid());
        passageQuestions.setText(request.getOrDefault("Text",""));
        passageQuestions.setScore(Integer.parseInt(request.getOrDefault("Score","0")));
        passageQuestions.setDifficulty(request.getOrDefault("Difficulty",""));
        passageQuestions.setOption1Text(request.getOrDefault("Option_1_text",""));
        passageQuestions.setOption2Text(request.getOrDefault("Option_2_text",""));
        passageQuestions.setOption3Text(request.getOrDefault("Option_3_text",""));
        passageQuestions.setOption4Text(request.getOrDefault("Option_4_text",""));
        passageQuestions.setOption5Text(request.getOrDefault("Option_5_text",""));
        Optional.ofNullable(request.get("PassageID")).map(Integer::parseInt)
                .flatMap(passageRepository::findById).ifPresent(passageQuestions::setPassage);

        log.trace("Submitting Passage question");
        PassageQuestions passageQuestions1 = passageQuestionsRepository.save(passageQuestions);
        log.debug("Passage created : {}", passageQuestions1.getStringSid());


        CommonV2Response commonV2Response = new CommonV2Response();
        commonV2Response.setId(passageQuestions1.getStringSid());
        commonV2Response.putField("Text", passageQuestions1.getText());
        commonV2Response.putField("Score", String.valueOf(passageQuestions1.getScore()));
        commonV2Response.putField("Difficulty", passageQuestions1.getDifficulty());
        commonV2Response.putField("Option_1_Text", passageQuestions1.getOption1Text());
        commonV2Response.putField("Option_2_Text", passageQuestions1.getOption2Text());
        commonV2Response.putField("Option_3_Text", passageQuestions1.getOption3Text());
        commonV2Response.putField("Option_4_Text", passageQuestions1.getOption4Text());
        commonV2Response.putField("Option_5_Text", passageQuestions1.getOption5Text());
        commonV2Response.putField("PassageID", Optional.ofNullable(passageQuestions1.getPassage()).map(Passage::getId)
                .map(String::valueOf).orElse(""));
        return generateResponse(Collections.singletonList(commonV2Response));
    }


    @Override
    public Map<String, List<CommonV2Response>> createPassageAnswer(Map<String, String> request){
        PassageAnswers passageAnswers = new PassageAnswers();
        passageAnswers.setSid(BaseEntity.generateByteUuid());
        passageAnswers.setUserExamId(request.getOrDefault("User_exam_id",""));
        passageAnswers.setStatus(request.getOrDefault("Status",""));
        passageAnswers.setScore(Integer.parseInt(request.getOrDefault("Grade","0")));

        Optional<PassageQuestions> questionId = Optional.ofNullable(request.getOrDefault("Question_id", ""))
                .map(Integer::parseInt).flatMap(passageQuestionsRepository::findById);
        passageAnswers.setPassageQuestion(questionId.orElseThrow(()-> new IncorrectArgumentException("Invalid Question id")));
        Optional<Users> author = Optional.ofNullable(request.getOrDefault("User_id","")).map(Integer::parseInt)
                .flatMap(userRepository::findById);
        passageAnswers.setUser(author.orElseThrow(()-> new IncorrectArgumentException("Invalid User Id")));

        log.trace("Submitting Passage question");
        PassageAnswers passageAnswers1 = passageAnswersRepository.save(passageAnswers);
        log.debug("Passage created : {}", passageAnswers1.getStringSid());


        CommonV2Response commonV2Response = new CommonV2Response();
        commonV2Response.setId(passageAnswers1.getStringSid());
        /*commonV2Response.putField("Text", passageAnswers1.getText());
        commonV2Response.putField("Score", String.valueOf(passageAnswers1.getScore()));
        commonV2Response.putField("Difficulty", passageAnswers1.getDifficulty());
        commonV2Response.putField("Option_1_Text", passageAnswers1.getOption1Text());
        commonV2Response.putField("Option_2_Text", passageAnswers1.getOption2Text());
        commonV2Response.putField("Option_3_Text", passageAnswers1.getOption3Text());
        commonV2Response.putField("Option_4_Text", passageAnswers1.getOption4Text());
        commonV2Response.putField("Option_5_Text", passageAnswers1.getOption5Text());
        commonV2Response.putField("PassageID", Optional.ofNullable(passageAnswers1.getPassage()).map(Passage::getId)
                .map(String::valueOf).orElse(""));*/
        return generateResponse(Collections.singletonList(commonV2Response));
    }

    @Override
    public Map<String, List<CommonV2Response>> getPassageAnswersByStatus(String status){

        List<PassageAnswers> passageAnswers = Optional.ofNullable(status).filter(StringUtils::isNotEmpty).map(passageAnswersRepository::findPassageAnswersByStatus)
                .orElseThrow(() -> new IncorrectArgumentException("Status is not correct"));
        log.debug("Passage answers by status : {} - {}", status, Optional.ofNullable(passageAnswers)
                .map(List::size).orElse(0));
        List<CommonV2Response> commonV2Responses = passageAnswers.stream().map(PassageV2Impl::createPassageAnswerResponse)
                .collect(Collectors.toList());
        return generateResponse(commonV2Responses);

    }

    @Override
    public Map<String, List<CommonV2Response>> getPassageAnswersByStatusAndUserId(String status,Integer userId){

        Users users = Optional.ofNullable(userId).flatMap(userRepository::findById)
                .orElseThrow(()->new IncorrectArgumentException("Incorrect User ID"));

        List<PassageAnswers> passageAnswers = Optional.ofNullable(status).filter(StringUtils::isNotEmpty)
                .map(stat->passageAnswersRepository.findPassageAnswersByStatusAndAndUser(stat, users))
                .orElseThrow(()->new IncorrectArgumentException("Incorrect Status"));

        List<CommonV2Response> commonV2Responses = passageAnswers.stream().map(PassageV2Impl::createPassageAnswerResponse)
                .collect(Collectors.toList());
        return generateResponse(commonV2Responses);

    }

    @Override
    public Map<String, List<CommonV2Response>> getPassageAnswersByUserIdAndExamId(Integer userId, String examId) {
        Users users = Optional.ofNullable(userId).flatMap(userRepository::findById)
                .orElseThrow(()->new IncorrectArgumentException("Incorrect User ID"));

        List<PassageAnswers> passageAnswers = Optional.ofNullable(examId).filter(StringUtils::isNotEmpty)
                .map(stat->passageAnswersRepository.findPassageAnswersByUserAndUserExamId(users, stat))
                .orElseThrow(()->new IncorrectArgumentException("Incorrect Status"));

        List<CommonV2Response> commonV2Responses = passageAnswers.stream().map(PassageV2Impl::createPassageAnswerResponse)
                .collect(Collectors.toList());
        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String, List<CommonV2Response>> updatePassageAnswers(String answerSid, Map<String, String> requests){

        log.trace("Updating passage answer sid : {} with Data: {}", answerSid,requests);
        String status = requests.getOrDefault("Status","");
        String userOption = requests.getOrDefault("User_option","");
        String userExplanation = requests.getOrDefault("User_explanation","");
        String explanation = requests.getOrDefault("explanation","");
        String score = requests.getOrDefault("Score","");
        PassageAnswers passageAnswers = passageAnswersRepository
                .findBySid(answerSid).orElseThrow(()->new IncorrectArgumentException("Invalid Answer sid"));
        Optional.ofNullable(userExplanation).filter(StringUtils::isNotEmpty).ifPresent(passageAnswers::setUserExplanation);
        Optional.ofNullable(userOption).filter(StringUtils::isNotEmpty).ifPresent(passageAnswers::setUserOption);
        passageAnswers.setDateofAnswer(Instant.now());
        Optional.ofNullable(score).filter(StringUtils::isNotEmpty).map(Integer::parseInt).ifPresent(passageAnswers::setScore);
        Optional.ofNullable(explanation).filter(StringUtils::isNotEmpty).ifPresent(passageAnswers::setExplanation);
        Optional.ofNullable(status).filter(StringUtils::isNotBlank).ifPresent(passageAnswers::setStatus);
        passageAnswersRepository.save(passageAnswers);
        log.debug("Passage Answer updated for answerId: {}",answerSid);

        return generateResponse(Collections.singletonList(createPassageAnswerResponse(passageAnswers)));

    }

    private static CommonV2Response createPassageAnswerResponse(PassageAnswers passageAnswers1) {
        CommonV2Response commonV2Response = new CommonV2Response();
        commonV2Response.setId(passageAnswers1.getStringSid());
        commonV2Response.putField("User_exam_id", passageAnswers1.getUserExamId());
        commonV2Response.putField("PassageText",passageAnswers1.getPassageQuestion().getPassage().getContent());
        commonV2Response.putField("Score",String.valueOf(passageAnswers1.getScore()));//need to understand where this will be coming from
        commonV2Response.putField("Date",String.valueOf(passageAnswers1.getDateofAnswer().getEpochSecond()));
        return commonV2Response;
    }

    public Map<String,List<CommonV2Response>> generateResponse(List<CommonV2Response> commonV2Responses){
        Map<String,List<CommonV2Response>> response = new HashMap<>();
        response.put("records",commonV2Responses);
        return response;
    }

}
