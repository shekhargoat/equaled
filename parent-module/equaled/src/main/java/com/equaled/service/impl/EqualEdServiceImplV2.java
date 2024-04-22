package com.equaled.service.impl;

import com.equaled.dozer.DozerUtils;
import com.equaled.entity.*;
import com.equaled.eserve.common.CommonUtils;
import com.equaled.eserve.common.exception.IncorrectArgumentException;
import com.equaled.eserve.common.exception.RecordNotFoundException;
import com.equaled.eserve.exception.errorcode.ErrorCodes;
import com.equaled.repository.*;
import com.equaled.service.IEqualEdServiceV2;
import com.equaled.to.CommonV2Request;
import com.equaled.to.CommonV2Response;
import com.equaled.to.CreateProfileRequest;
import com.equaled.value.EqualEdEnums;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class EqualEdServiceImplV2 implements IEqualEdServiceV2 {

    IDashboardRepository dashboardRepository;
    ISubjectCategoryRepository subjectCategoryRepository;
    ITestRepository testRepository;
    IQuestionRepository questionRepository;
    IUserRepository userRepository;
    ISetPracticeRepository setPracticeRepository;
    IYearGroupRepository yearGroupRepository;
    IAccountRepository accountRepository;
    ISubjectRepository subjectRepository;
    IUseranswerRepository useranswerRepository;
    IImprovementRepository improvementRepository;
    IPracticeUseranswerRepository practiceUseranswerRepository;

    DozerUtils mapper;

    @Override
    public Map<String,List<CommonV2Response>> getDashboardsByUser(Integer userId) {
        log.trace("Finding Dashboard By User: {}",userId);

        List<Dashboard> dashboardsByUserId = Optional.ofNullable(dashboardRepository.findDashboardsByUserId(userId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Dashboard fetched for User {} = {}",userId, dashboardsByUserId.size());

        List<CommonV2Response> commonV2Responses = dashboardsByUserId.stream().map(EqualEdServiceImplV2::createDashboardResponse).collect(Collectors.toList());

        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String,List<CommonV2Response>> getDashboardsByUser(String userId) {
        log.trace("Finding Dashboard By User: {}",userId);
        List<Dashboard> dashboardsByUserId = Optional.ofNullable(dashboardRepository.findDashboardsByUserSid(userId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Dashboard fetched for User {} = {}",userId, dashboardsByUserId.size());
        List<CommonV2Response> commonV2Responses = dashboardsByUserId.stream().map(EqualEdServiceImplV2::createDashboardResponse).collect(Collectors.toList());

        return generateResponse(commonV2Responses);
    }

    private static CommonV2Response createDashboardResponse(Dashboard dashboard) {
        CommonV2Response commonV2Response = new CommonV2Response();
        commonV2Response.setId(dashboard.getStringSid());
        commonV2Response.putField("exam_id", dashboard.getExamId());
        commonV2Response.putField("user_id",String.valueOf(dashboard.getUser().getId()));
        commonV2Response.putField("subject_name", dashboard.getSubject().getName());
        commonV2Response.putField("title", dashboard.getTitle());
        commonV2Response.putField("start_time",
                LocalDateTime.ofInstant(dashboard.getStartTime(),
                        ZoneId.of("UTC")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return commonV2Response;
    }

    @Override
    public Map<String,List<CommonV2Response>> getSubjectCategoriedByYear(Integer yearGroupId){
        log.trace("Finding Catgories by Year: {}",yearGroupId);
        List<SubjectCategories> categories = Optional.ofNullable(subjectCategoryRepository
                        .findSubjectsCategoriesByYrGroupId(yearGroupId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Categories fetched for year {} = {}",yearGroupId, categories.size());

        List<CommonV2Response> commonV2Responses = categories.stream().map(category -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(category.getStringSid());
            commonV2Response.putField("year_group_id", String.valueOf(category.getYearGroup().getYear()));
            commonV2Response.putField("category",category.getSubject().getName());
            commonV2Response.putField("sub-Category", category.getSubCategory());
            commonV2Response.putField("sub-Category_1", category.getSubCategory1());
            return commonV2Response;
        }).collect(Collectors.toList());


        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String,List<CommonV2Response>> getTestsByYearAndSubjectName(Integer yearGroupId, String subjectName){
        log.trace("Finding Tests for Year {} and subjectName {}",yearGroupId, subjectName);
        List<Test> tests = Optional.ofNullable(testRepository.getTestByYearAndSubject(yearGroupId, subjectName))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Tests fetched for year {} = {}",yearGroupId, tests.size());

        List<CommonV2Response> commonV2Responses = tests.stream().map(test -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(test.getStringSid());
            commonV2Response.putField("test_id", String.valueOf(test.id));
            commonV2Response.putField("Test Description", test.getDescription());
            commonV2Response.putField("Time", String.valueOf(test.getTimeAllottedInMins()));
            commonV2Response.putField("Subject", test.getSubject().getName());
            commonV2Response.putField("Subject_id", String.valueOf(test.getSubject().id));
            commonV2Response.putField("year_group_id", String.valueOf(test.getYearGroupId().getYear()));
            commonV2Response.putField("No_Questions", String.valueOf(test.getNoOfQuestions()));
            commonV2Response.putField("Learn", String.valueOf(test.getTestType()));
            return commonV2Response;
        }).collect(Collectors.toList());

        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String,List<CommonV2Response>> getQuestionsBySubAndSubcat(Integer subjectId, String subcatName){
        log.trace("Finding Questions for subject {} and subcat Name {}",subjectId, subcatName);
        List<Questions> questions = Optional.ofNullable(questionRepository
                .getQuestionsBySubAndSubcat(subjectId, subcatName)).orElse(ListUtils.EMPTY_LIST);
        log.debug("Finding Questions for subject {} and subcat Name {} = {}",subjectId, subcatName, questions.size());
        List<CommonV2Response> commonV2Responses = questions.stream().map(question -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(question.getStringSid());
            commonV2Response.putField("question_id", String.valueOf(question.id));
            commonV2Response.putField("Text", question.getQuestion());
            commonV2Response.putField("Subject_id", String.valueOf(question.getSubject().id));
            commonV2Response.putField("year_group_id", String.valueOf(question.getYearGroupId().id));
            commonV2Response.putField("Difficulty_level", String.valueOf(question.getDifficulty()));
            commonV2Response.putField("category", String.valueOf(question.getCategory()));
            commonV2Response.putField("sub_category", String.valueOf(question.getSubCategory()));
            commonV2Response.putField("Correct_option", String.valueOf(question.getCorrectOption()));
            commonV2Response.putField("Learn", String.valueOf(question.getLearn()));

            return commonV2Response;
        }).collect(Collectors.toList());

        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String,List<CommonV2Response>> getUserById(Integer id){
        log.trace("Finding user by id : {}",id);
        return userRepository.findById(id).map(users -> generateResponse(Collections.singletonList(createCommonUserResponse(users)))
        ).orElseThrow(() -> new RecordNotFoundException(ErrorCodes.U001,"User not found for given id "+id));
    }

    @Override
    public Map<String, List<CommonV2Response>> getSetpracticeByUserIdSubjectName(Integer userId, String practiceName, String subjectName){
        log.trace("Finding Set practice for user {} and practiceName = {}, subject = {}", userId, practiceName, subjectName);
        List<Setpractice> setpractices = Optional.ofNullable(setPracticeRepository
                .getSetpracticeByUserIdSubjectName(userId, practiceName, subjectName)).orElse(ListUtils.EMPTY_LIST);
        log.debug("Set Practices found  for user id {} subject {} and practice {} = {}",userId, subjectName, practiceName, setpractices.size());

        List<CommonV2Response> commonV2Responses = setpractices.stream().map(setpractice -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(setpractice.getStringSid());
            commonV2Response.putField("seq_id", String.valueOf(setpractice.id));
            commonV2Response.putField("User_id", String.valueOf(setpractice.getUser().id));
            commonV2Response.putField("practicename", setpractice.getPracticeName());
            commonV2Response.putField("time_limit", String.valueOf(setpractice.getTimeLimit()));
            commonV2Response.putField("questions", setpractice.getQuestions());
            commonV2Response.putField("no_questions", String.valueOf(setpractice.getNoOfQ()));
            commonV2Response.putField("subject_name", setpractice.getSubject().getName());
            commonV2Response.putField("Status", setpractice.getStatus().name());
            return commonV2Response;
        }).collect(Collectors.toList());

        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String, List<CommonV2Response>> createProfile(CreateProfileRequest request) {
        List<Users> users = request.getRecords().stream()
                .map(record -> {
                    Users user = new Users();
                    user.setSid(BaseEntity.generateByteUuid());
                    user.setUsername(record.getFields().get("Username"));
                    user.setEmail(record.getFields().get("Email"));
                    user.setPassword(record.getFields().get("Password"));
                    user.setYearGroup(getOrCreateYearGroup(Integer.parseInt(record.getFields().get("year_group_id"))));
                    Accounts accounts= Optional.ofNullable(record.getFields().get("account_id")).filter(StringUtils::isNumeric)
                                    .map(Integer::parseInt).map(ids -> getOrCreateAccount(ids, record.getFields().get("Username")))
                            .orElse(accountRepository.findById(1).get());
                    user.setRelatedAccount(accounts);
                    user.setRole(EqualEdEnums.UserRole.valueOf(record.getFields().get("role").toUpperCase()));
                    user.setLastLogin(Instant.now());
                    user.setLastUpdatedOn(Instant.now());
                    return user;
                }).collect(Collectors.toList());

        List<Users> usersCreated = userRepository.saveAll(users);
        List<CommonV2Response> commonV2Responses = usersCreated.stream()
                .map(EqualEdServiceImplV2::createCommonUserResponse).collect(Collectors.toList());
        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String, List<CommonV2Response>> createDashboard(CommonV2Request request) {
        Map<String, String> fields = request.getFields();
        Dashboard dashboard = new Dashboard();
        dashboard.setSid(BaseEntity.generateByteUuid());
        try {
            int userId = Integer.parseInt(fields.get("user_id"));
            Optional<Users> optional = userRepository.findById(userId);
            dashboard.setUser(optional.orElseThrow(() -> new RecordNotFoundException(ErrorCodes.U001,"User not found for given id "+userId)));
        }catch (Exception e){
            throw new IncorrectArgumentException(ErrorCodes.U002,"Invalid user id, provide valid user id");
        }

        List<Subject> subjects = Optional.ofNullable(subjectRepository.findByName(fields.get("subject_name"))).orElse(Collections.EMPTY_LIST);
        if(subjects.isEmpty()){
            throw new RecordNotFoundException(ErrorCodes.S001,"Subject not found for name "+fields.get("subject_name"));
        }
        dashboard.setSubject(subjects.get(0));
        dashboard.setExamId(fields.get("exam_id"));
        dashboard.setTitle(fields.get("title"));
        dashboard.setStartTime(Instant.now());

        dashboardRepository.save(dashboard);
        return generateResponse(Collections.singletonList(createDashboardResponse(dashboard)));
    }

    private Accounts getOrCreateAccount(int accountId,String name) {
        return accountRepository.findById(accountId)
                .orElseGet(() -> {
                    Accounts accounts = new Accounts();
                    accounts.setSid(BaseEntity.generateByteUuid());
                    accounts.setName(name);
                    accounts.setCreatedOn(Instant.now());
                    accounts.setLastUpdatedOn(Instant.now());
                    return accountRepository.save(accounts);
                });
    }

    private YearGroup getOrCreateYearGroup(int yearGroupId) {
        return yearGroupRepository.findById(yearGroupId)
                .orElseGet(() -> {
                    YearGroup yearGroup = new YearGroup();
                    yearGroup.setSid(BaseEntity.generateByteUuid());
                    yearGroup.setYear(yearGroupId);
                    return yearGroupRepository.save(yearGroup);
                });
    }


    public Map<String,List<CommonV2Response>> generateResponse(List<CommonV2Response> commonV2Responses){
        Map<String,List<CommonV2Response>> response = new HashMap<>();
        response.put("records",commonV2Responses);
        return response;
    }

    @Override
    public Map<String, List<CommonV2Response>> submitAnswer(Map<String, String> answer){
        if(MapUtils.isEmpty(answer)) throw new IncorrectArgumentException("Answer information is not available");
        log.trace("Submitting answers : {}",answer );
        UserAnswers userAnswers = new UserAnswers();
        userAnswers.setSid(BaseEntity.generateByteUuid());

        userAnswers.setUser(userRepository.findById(MapUtils.getIntValue(answer, "User_id"))
                .orElseThrow(()-> new IncorrectArgumentException("Invalid User Id")));
        userAnswers.setQuestion(questionRepository.findById(MapUtils.getIntValue(answer, "question_id"))
                .orElseThrow(()-> new IncorrectArgumentException("Invalid Question Id")));
        userAnswers.setExamId(MapUtils.getString(answer, "exam_id"));

        // get user answer date
        userAnswers.setAnswerDate(Instant.now());
        userAnswers.setTimeSpent(MapUtils.getIntValue(answer, "Time_Spent"));
        userAnswers.setExplanation(MapUtils.getString(answer, "Explanation"));
        userAnswers.setUserOption(MapUtils.getString(answer, "User_option"));
        userAnswers.setCorrectOption(MapUtils.getString(answer, "Correct_option","a"));
        userAnswers.setExamId(MapUtils.getString(answer, "exam_id"));
        userAnswers.setAnswerDate(Instant.now());
        log.trace("Submitting answer");
        UserAnswers userAnswers1 = useranswerRepository.save(userAnswers);
        log.debug("Users answer created : {}", userAnswers.getSid());
        CommonV2Response commonV2Response = new CommonV2Response();
        commonV2Response.setId(userAnswers1.getStringSid());
        commonV2Response.setCreatedTime(userAnswers1.getAnswerDate().toString());
        commonV2Response.putField("user_exam_Id", String.valueOf(userAnswers1.id));
        commonV2Response.putField("User_id", String.valueOf(userAnswers1.getUser().getId()));
        commonV2Response.putField("exam_id", userAnswers1.getExamId());
        commonV2Response.putField("text", userAnswers1.getQuestion().getQuestion());
        commonV2Response.putField("category", userAnswers1.getQuestion().getCategory());
        commonV2Response.putField("sub_category", userAnswers1.getQuestion().getSubCategory());
        commonV2Response.putField("Correct_option", userAnswers1.getQuestion().getCorrectOption());
        commonV2Response.putField("question_id", String.valueOf(userAnswers1.getQuestion().id));
        commonV2Response.putField("Explanation", userAnswers1.getExplanation());
        commonV2Response.putField("Time_Spent", String.valueOf(userAnswers1.getTimeSpent()));
        commonV2Response.putField("date", userAnswers1.getAnswerDate().toString());
        return generateResponse(Collections.singletonList(commonV2Response));
    }

    @Override
    public CommonV2Response saveImprovement(Map<String, String> improvement){
        if(MapUtils.isEmpty(improvement)) throw new IncorrectArgumentException("Improvement information is not available");
        log.trace("Submitting Improvement : {}",improvement );

        Improvement improvement1 = new Improvement();
        improvement1.generateUuid();

        improvement1.setUser(userRepository.findById(MapUtils.getIntValue(improvement, "user_id"))
                .orElseThrow(()-> new IncorrectArgumentException("Invalid User Id")));
        Subject subject = Optional.ofNullable(subjectRepository.findByName(MapUtils.getString(improvement, "subject_name")))
                        .filter(CollectionUtils::isNotEmpty).map(lst -> lst.get(0))
                .orElseThrow(()-> new IncorrectArgumentException("Invalid Subject Name"));
        improvement1.setSubject(subject);

        improvement1.setExamId(MapUtils.getString(improvement, "exam_id"));
        improvement1.setScore(Integer.parseInt(MapUtils.getString(improvement, "score")));
        improvement1.setStrongCategory(MapUtils.getString(improvement, "strong_category"));
        improvement1.setWeakCategory(MapUtils.getString(improvement, "weak_category"));
        improvement1.setTotalQuestions(Integer.parseInt(MapUtils.getString(improvement, "total_questions")));
        improvement1.setCreatedOn(Instant.now());

        log.trace("Submitting Improvement");
        Improvement improvement2 = improvementRepository.save(improvement1);
        log.debug("Users Improvement created : {}", improvement2.getSid());
        return createImprovementResponse(improvement2);
    }

    @Override
    public void markSetpracticeClose(String setPracticeSid){
        Optional.ofNullable(setPracticeSid).map(StringUtils::isNotEmpty)
                .orElseThrow(() -> new IncorrectArgumentException("Record sid is missing"));
        setPracticeRepository.markStatus(setPracticeSid, EqualEdEnums.SetpracticeStatus.COMPLETED);
    }

    @Override
    public CommonV2Response updateUserLastLogin(Integer userId, CommonV2Request request) {
        log.trace("Updating user last login time for id : {}",userId);
        return userRepository.findById(userId)
                .map(users -> {
                    users.setLastLogin(Instant.parse(request.getFields().get("lastlogin")));
                    return createCommonUserResponse(userRepository.saveAndFlush(users));
                }).orElseThrow(() -> new RecordNotFoundException(ErrorCodes.U001, "User not found for given id " + userId));
    }
    @Override
    public Map<String, List<CommonV2Response>> getImprovementsByUser(Integer userId) {
        log.trace("Finding improvements for userId {}",userId);
        List<Improvement> improvements = improvementRepository.getImprovementsByUserId(userId);
//        if(improvements.isEmpty()) throw new RecordNotFoundException(ErrorCodes.I001, "Improvements not found for given user id " + userId);
        log.debug("Found {} improvements for userId {}",improvements.size(),userId);
        return createImprovementResponse(improvements);
    }

    @Override
    public Map<String, List<CommonV2Response>> getImprovementsByExam(String examId) {
        log.trace("Finding improvements for examId {}",examId);
        List<Improvement> improvements = improvementRepository.getImprovementsByExamId(examId);
//        if(improvements.isEmpty()) throw new RecordNotFoundException(ErrorCodes.I001, "Improvements not found for given exam id " + examId);
        log.debug("Found {} improvements for examId {}",improvements.size(),examId);
        return createImprovementResponse(improvements);
    }

    @Override
    public Map<String, List<CommonV2Response>> getImprovementsByUserIdAndExam(Integer userId,String examId) {
        log.trace("Finding improvements for userId {} and examId {}",userId,examId);
        List<Improvement> improvements = improvementRepository.getImprovementsByUserIdAndExamId(userId,examId);
//        if(improvements.isEmpty()) throw new RecordNotFoundException(ErrorCodes.I001, "Improvements not found for given user id " + userId +" and examId "+examId);
        log.debug("Found {} improvements for userId {} and examId {}",improvements.size(),userId,examId);
        return createImprovementResponse(improvements);
    }

    @Override
    public Map<String, List<CommonV2Response>> getUserAnswersByExamId(String examId) {
        log.trace("Finding user answers for examId {}",examId);
        List<UserAnswers> userAnswers = useranswerRepository.findByExamId(examId);
//        if(userAnswers.isEmpty()) throw new RecordNotFoundException(ErrorCodes.UA001, "User answers not found for given and examId "+examId);
        log.debug("Found {} user answers for examId {}",userAnswers.size(),examId);
        return createUseranswerResponse(userAnswers);
    }

    private Map<String, List<CommonV2Response>> createUseranswerResponse(List<UserAnswers> userAnswers) {
        List<CommonV2Response> commonV2Responses = userAnswers.stream().map(answers -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(answers.getStringSid());
            commonV2Response.setCreatedTime(answers.getAnswerDate().toString());
            commonV2Response.putField("user_exam_Id", String.valueOf(answers.id));
            commonV2Response.putField("User_id", String.valueOf(answers.getUser().getId()));
            commonV2Response.putField("exam_id", answers.getExamId());
            commonV2Response.putField("text", answers.getQuestion().getQuestion());
            commonV2Response.putField("category", answers.getQuestion().getCategory());
            commonV2Response.putField("sub_category", answers.getQuestion().getSubCategory());
            commonV2Response.putField("Correct_option", answers.getQuestion().getCorrectOption());
            commonV2Response.putField("question_id", String.valueOf(answers.getQuestion().id));
            commonV2Response.putField("Explanation", answers.getExplanation());
            commonV2Response.putField("Time_Spent", String.valueOf(answers.getTimeSpent()));
            commonV2Response.putField("date", answers.getAnswerDate().toString());
            commonV2Response.putField("User_option", Optional.ofNullable(answers.getUserOption()).orElse(StringUtils.EMPTY));
            return commonV2Response;
        }).collect(Collectors.toList());

        return generateResponse(commonV2Responses);
    }


    private Map<String, List<CommonV2Response>> createImprovementResponse(List<Improvement> improvements) {
        List<CommonV2Response> commonV2Responses = improvements.stream().map(improvement -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(improvement.getStringSid());
            commonV2Response.setCreatedTime(Optional.ofNullable(improvement.getCreatedOn()).map(Instant::toString).orElse(Instant.now().toString()));
            commonV2Response.putField("improve_id", String.valueOf(improvement.id));
            commonV2Response.putField("user_id", String.valueOf(improvement.getUser().getId()));
            commonV2Response.putField("exam_id", improvement.getExamId());
            commonV2Response.putField("strong_category", improvement.getStrongCategory());
            commonV2Response.putField("weak_category", improvement.getWeakCategory());
            commonV2Response.putField("score", String.valueOf(improvement.getScore()));
            commonV2Response.putField("total_questions", String.valueOf(improvement.getTotalQuestions()));
            commonV2Response.putField("subject_name", improvement.getSubject().getName());

            return commonV2Response;
        }).collect(Collectors.toList());

        return generateResponse(commonV2Responses);
    }

    private CommonV2Response createImprovementResponse(Improvement improvement) {
        CommonV2Response commonV2Response = new CommonV2Response();
        commonV2Response.setId(improvement.getStringSid());
        commonV2Response.setCreatedTime(improvement.getCreatedOn().toString());
        commonV2Response.putField("improve_id", String.valueOf(improvement.id));
        commonV2Response.putField("user_id", String.valueOf(improvement.getUser().getId()));
        commonV2Response.putField("exam_id", improvement.getExamId());
        commonV2Response.putField("strong_category", improvement.getStrongCategory());
        commonV2Response.putField("weak_category", improvement.getWeakCategory());
        commonV2Response.putField("score", String.valueOf(improvement.getScore()));
        commonV2Response.putField("total_questions", String.valueOf(improvement.getTotalQuestions()));
        commonV2Response.putField("subject_name", improvement.getSubject().getName());

        return commonV2Response;
    }

    private static CommonV2Response createCommonUserResponse(Users users) {
        CommonV2Response commonV2Response = new CommonV2Response();
        commonV2Response.setId(users.getStringSid());
        commonV2Response.setCreatedTime(Instant.now().toString());
        commonV2Response.putField("Username", users.getUsername());
        commonV2Response.putField("Email", users.getEmail());
        commonV2Response.putField("Password", users.getPassword());
        commonV2Response.putField("User_id", String.valueOf(users.getId()));
        commonV2Response.putField("year_group_id", String.valueOf(users.getYearGroup().getId()));
        commonV2Response.putField("role", WordUtils.capitalizeFully(users.getRole().name().toLowerCase()));
        commonV2Response.putField("lastlogin", LocalDateTime.ofInstant(users.getLastLogin(),
                ZoneId.of("UTC")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return commonV2Response;
    }

    @Override
    public Map<String, List<CommonV2Response>> submitPracticeAnswer(Map<String, String> answer){
        if(MapUtils.isEmpty(answer)) throw new IncorrectArgumentException("Answer information is not available");
        log.trace("Submitting answers : {}",answer );
        PracticeUserAnswers userAnswers = new PracticeUserAnswers();
        userAnswers.setSid(BaseEntity.generateByteUuid());

        userAnswers.setUser(userRepository.findById(MapUtils.getIntValue(answer, "User_id"))
                .orElseThrow(()-> new IncorrectArgumentException("Invalid User Id")));
        userAnswers.setQuestion(questionRepository.findById(MapUtils.getIntValue(answer, "question_id"))
                .orElseThrow(()-> new IncorrectArgumentException("Invalid Question Id")));
        userAnswers.setExamId(MapUtils.getString(answer, "exam_id"));
        // get user answer date
        userAnswers.setAnswerDate(Instant.now());
        userAnswers.setTimeSpent(MapUtils.getIntValue(answer, "Time_Spent"));
        userAnswers.setExplanation(MapUtils.getString(answer, "Explanation"));
        userAnswers.setUserOption(MapUtils.getString(answer, "User_option"));
        userAnswers.setCorrectOption(MapUtils.getString(answer, "Correct_option"));

        log.trace("Submitting answer");
        PracticeUserAnswers userAnswers1 = practiceUseranswerRepository.save(userAnswers);
        log.debug("Users answer created : {}", userAnswers.getSid());
        CommonV2Response commonV2Response = new CommonV2Response();
        commonV2Response.setId(userAnswers1.getStringSid());
        commonV2Response.setCreatedTime(userAnswers1.getAnswerDate().toString());
        commonV2Response.putField("user_exam_Id", String.valueOf(userAnswers1.id));
        commonV2Response.putField("User_id", String.valueOf(userAnswers1.getUser().getId()));
        commonV2Response.putField("exam_id", userAnswers1.getExamId());
        commonV2Response.putField("text", userAnswers1.getQuestion().getQuestion());
        commonV2Response.putField("category", userAnswers1.getQuestion().getCategory());
        commonV2Response.putField("sub_category", userAnswers1.getQuestion().getSubCategory());
        commonV2Response.putField("Correct_option", userAnswers1.getQuestion().getCorrectOption());
        commonV2Response.putField("question_id", String.valueOf(userAnswers1.getQuestion().id));
        commonV2Response.putField("Explanation", userAnswers1.getExplanation());
        commonV2Response.putField("Time_Spent", String.valueOf(userAnswers1.getTimeSpent()));
        commonV2Response.putField("date", userAnswers1.getAnswerDate().toString());
        return generateResponse(Collections.singletonList(commonV2Response));
    }

    @Override
    public Map<String, List<CommonV2Response>> getQuestionsBySubAndLearnType(Integer subjectId, String learnType) {
        log.trace("Finding Questions for subject {} and learn type {}",subjectId, learnType);
        List<Questions> questions = Optional.ofNullable(questionRepository
                .getQuestionsBySubjectAndLearn(subjectId, EqualEdEnums.LearnType.valueOf(learnType))).orElse(ListUtils.EMPTY_LIST);
        log.debug("Finding Questions for subject {} and learn type {} = {}",subjectId, learnType, questions.size());
        return createQuestionsResponse(questions);
    }

    @Override
    public Map<String, List<CommonV2Response>> getSetpracticeByUserIdAndStatus(Integer userId, String status) {
        log.trace("Finding Set practice for user {} and status {}", userId, status);
        List<Setpractice> setpractices = Optional.ofNullable(setPracticeRepository
                .getSetpracticeByUserAndStatus(userId, EqualEdEnums.SetpracticeStatus.valueOf(status))).orElse(ListUtils.EMPTY_LIST);
        log.debug("Found {} Set Practices found  for user id {} and status {}",setpractices.size(),userId,status);

        List<CommonV2Response> commonV2Responses = setpractices.stream().map(setpractice -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(setpractice.getStringSid());
            commonV2Response.putField("seq_id", String.valueOf(setpractice.id));
            commonV2Response.putField("User_id", String.valueOf(setpractice.getUser().id));
            commonV2Response.putField("practicename", setpractice.getPracticeName());
            commonV2Response.putField("time_limit", String.valueOf(setpractice.getTimeLimit()));
            commonV2Response.putField("questions", setpractice.getQuestions());
            commonV2Response.putField("no_questions", String.valueOf(setpractice.getNoOfQ()));
            commonV2Response.putField("subject_name", setpractice.getSubject().getName());
            commonV2Response.putField("Status", setpractice.getStatus().name());
            return commonV2Response;
        }).collect(Collectors.toList());

        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String, List<CommonV2Response>> getTestsByYearGroup(Integer yearGroupId) {
        log.trace("Finding Tests for Year {}",yearGroupId);
        List<Test> tests = Optional.ofNullable(testRepository.getTestByYearGroupId(yearGroupId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Tests fetched for year {} = {}",yearGroupId, tests.size());

        List<CommonV2Response> commonV2Responses = tests.stream().map(test -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(test.getStringSid());
            commonV2Response.putField("test_id", String.valueOf(test.id));
            commonV2Response.putField("Test Description", test.getDescription());
            commonV2Response.putField("Time", String.valueOf(test.getTimeAllottedInMins()));
            commonV2Response.putField("Subject", test.getSubject().getName());
            commonV2Response.putField("Subject_id", String.valueOf(test.getSubject().id));
            commonV2Response.putField("year_group_id", String.valueOf(test.getYearGroupId().getYear()));
            commonV2Response.putField("No_Questions", String.valueOf(test.getNoOfQuestions()));
            commonV2Response.putField("Learn", String.valueOf(test.getTestType()));
            return commonV2Response;
        }).collect(Collectors.toList());

        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String,List<CommonV2Response>> getUserByUserName(String username){
        log.trace("Finding user by username : {}",username);
        return userRepository.findByUsernameIs(username).map(users -> generateResponse(Collections.singletonList(createCommonUserResponse(users)))
        ).orElseThrow(() -> new RecordNotFoundException(ErrorCodes.U001,"User not found for given username "+username));
    }


    @Override
    public Map<String, List<CommonV2Response>> getQuestionsBySubAndYearGroup(String subjectName, Integer yearGroup) {
        log.trace("Finding Questions for subject {} and year group {}",subjectName, yearGroup);
        List<Questions> questions = Optional.ofNullable(questionRepository
                .getQuestionsBySubjectAndYearGroupId(subjectName, yearGroup)).orElse(ListUtils.EMPTY_LIST);
        log.debug("Finding Questions for subject {} and year group {} = {}",subjectName, yearGroup, questions.size());
        return createQuestionsResponse(questions);
    }

    @Override
    public Map<String, List<CommonV2Response>> getUserAnswersByUserAndExamId(Integer userId, String examId) {
        log.trace("Finding user answers for userId {} and examId {}",userId,examId);
        List<UserAnswers> userAnswers = useranswerRepository.findByUserAndExamId(userId, examId);
//        if(userAnswers.isEmpty()) throw new RecordNotFoundException(ErrorCodes.UA001, "User answers not found for given user "+userId +" and examId "+examId);
        log.debug("Found {} user answers for userId {} examId {}",userAnswers.size(),userId,examId);
        return createUseranswerResponse(userAnswers);
    }

    @Override
    public Map<String, List<CommonV2Response>> getUserBySid(String sid) {
        log.trace("Finding user by sid : {}",sid);
        return userRepository.findBySid(sid).map(users -> generateResponse(Collections.singletonList(createCommonUserResponse(users)))
        ).orElseThrow(() -> new RecordNotFoundException(ErrorCodes.U001,"User not found for given sid "+sid));
    }

    @Override
    public Map<String, List<CommonV2Response>> submitSetpractice(Map<String, List<CommonV2Request>> practiceRequest){
        log.trace("Saving practice set : {}", CommonUtils.toJsonFunction.apply(practiceRequest));

        List<Setpractice> setpractices = new ArrayList<>();
        List<CommonV2Request> practiceList = practiceRequest.get("records");
        practiceList.forEach(setpractice -> {
            Setpractice setpractice1 = new Setpractice();
            setpractice1.generateUuid();

            setpractice1.setUser(userRepository.findById(MapUtils.getIntValue(setpractice.getFields(), "User_id"))
                    .orElseThrow(()-> new IncorrectArgumentException("Invalid User Id")));
            Subject subject = Optional.ofNullable(subjectRepository.findByName(MapUtils.getString(setpractice.getFields()
                            , "subject_name")))
                    .filter(CollectionUtils::isNotEmpty).map(lst -> lst.get(0))
                    .orElseThrow(()-> new IncorrectArgumentException("Invalid Subject Name"));
            setpractice1.setSubject(subject);

            setpractice1.setStatus(EqualEdEnums.SetpracticeStatus.PENDING);
            setpractice1.setQuestions(MapUtils.getString(setpractice.getFields(), "questions"));
            setpractice1.setPracticeName(MapUtils.getString(setpractice.getFields(), "practicename"));
            setpractice1.setNoOfQ(MapUtils.getInteger(setpractice.getFields(), "no_questions"));
            setpractice1.setTimeLimit(MapUtils.getInteger(setpractice.getFields(), "time_limit"));

            setpractices.add(setpractice1);
        });

        if(CollectionUtils.isNotEmpty(setpractices)){
            List<Setpractice> setpractices1 = setPracticeRepository.saveAll(setpractices);
            List<CommonV2Response> commonV2Responses = setpractices1.stream().map(setpractice -> {
                CommonV2Response commonV2Response = new CommonV2Response();
                commonV2Response.setId(setpractice.getStringSid());
                commonV2Response.putField("seq_id", String.valueOf(setpractice.id));
                commonV2Response.putField("User_id", String.valueOf(setpractice.getUser().id));
                commonV2Response.putField("practicename", setpractice.getPracticeName());
                commonV2Response.putField("time_limit", String.valueOf(setpractice.getTimeLimit()));
                commonV2Response.putField("questions", setpractice.getQuestions());
                commonV2Response.putField("no_questions", String.valueOf(setpractice.getNoOfQ()));
                commonV2Response.putField("subject_name", setpractice.getSubject().getName());
                commonV2Response.putField("Status", setpractice.getStatus().name());
                return commonV2Response;
            }).collect(Collectors.toList());
            return generateResponse(commonV2Responses);

        }else return null;
    }

    @Override
    public Map<String,List<CommonV2Response>> getUserByEmail(String email){
        log.trace("Finding user by email : {}",email);
        return userRepository.findByEmailIs(email).map(users -> generateResponse(Collections.singletonList(createCommonUserResponse(users)))
        ).orElseThrow(() -> new RecordNotFoundException(ErrorCodes.U001,"User not found for given email "+email));
    }

    @Override
    public Map<String,List<CommonV2Response>> getSuggedtedDashboardsByUser(Integer userId) {
        log.trace("Finding Dashboard By User: {}",userId);

        List<Dashboard> dashboardsByUserId = Optional.ofNullable(dashboardRepository.findSuggestedDashboardsByUserId(userId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Dashboard fetched for User {} = {}",userId, dashboardsByUserId.size());

        List<CommonV2Response> commonV2Responses = dashboardsByUserId.stream()
                .map(EqualEdServiceImplV2::createDashboardResponse).collect(Collectors.toList());

        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String, List<CommonV2Response>> getUserAnswersByUserId(Integer userId) {
        log.trace("Finding user answers for userId {} ",userId);
        List<UserAnswers> userAnswers = useranswerRepository.findByUserId(userId);
        log.debug("Found {} user answers for userId {} ",userAnswers.size(),userId);
        return createUseranswerResponse(userAnswers);
    }

    @Override
    public Map<String, List<CommonV2Response>> getPracticeAnswersByUserId(Integer userId) {
        log.trace("Finding user answers for userId {} ",userId);
        List<PracticeUserAnswers> practiceUserAnswers = practiceUseranswerRepository.findByUserId(userId);
        log.debug("Found {} user answers for userId {} ",practiceUserAnswers.size(),userId);
        List<CommonV2Response> commonV2Responses = practiceUserAnswers.stream().map(answers -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(answers.getStringSid());
            commonV2Response.setCreatedTime(answers.getAnswerDate().toString());
            commonV2Response.putField("user_exam_Id", String.valueOf(answers.id));
            commonV2Response.putField("User_id", String.valueOf(answers.getUser().getId()));
            commonV2Response.putField("exam_id", answers.getExamId());
            commonV2Response.putField("text", answers.getQuestion().getQuestion());
            commonV2Response.putField("category", answers.getQuestion().getCategory());
            commonV2Response.putField("sub_category", answers.getQuestion().getSubCategory());
            commonV2Response.putField("Correct_option", answers.getQuestion().getCorrectOption());
            commonV2Response.putField("question_id", String.valueOf(answers.getQuestion().id));
            commonV2Response.putField("Explanation", answers.getExplanation());
            commonV2Response.putField("Time_Spent", String.valueOf(answers.getTimeSpent()));
            commonV2Response.putField("date", answers.getAnswerDate().toString());
            return commonV2Response;
        }).collect(Collectors.toList());

        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String, List<CommonV2Response>> getNonWeakCategoryImprovementsByUserId(Integer userId) {
        log.trace("Finding improvements for userid {}",userId);
        List<Improvement> improvements = improvementRepository.getNonWeakCatImprovementByUserId(userId);
        log.debug("Found {} improvements for userId {}",improvements.size(),userId);
        return createImprovementResponse(improvements);
    }
    @Override
    public Map<String, List<CommonV2Response>> getNonStrongCategoryImprovementsByUserId(Integer userId) {
        log.trace("Finding improvements for userid {}",userId);
        List<Improvement> improvements = improvementRepository.getNonStrongCatImprovementByUserId(userId);
        log.debug("Found {} improvements for userId {}",improvements.size(),userId);
        return createImprovementResponse(improvements);
    }

    @Override
    public Map<String,List<CommonV2Response>> getAllUsers(){
        List<Users> users = userRepository.findAll();
        if(CollectionUtils.isNotEmpty(users)){
            return generateResponse(users.stream().map(EqualEdServiceImplV2::createCommonUserResponse)
                    .collect(Collectors.toList()));
        }else throw new RecordNotFoundException(ErrorCodes.U001,"User not found");
    }

    @Override
    public Map<String, List<CommonV2Response>> addStudentsForTeacher(Integer teacherId, List<Integer> students){
        Users teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new RecordNotFoundException(ErrorCodes.U003,ErrorCodes.U003.getMsg()));

        log.trace("Adding teacher {} for students {}",teacherId, students);
        List<Users> studentUsers = userRepository.findAllById(students);
        if(CollectionUtils.isEmpty(studentUsers)) new RecordNotFoundException(ErrorCodes.U004,ErrorCodes.U004.getMsg());
        log.trace("Adding teacher {} for students {}",teacherId, studentUsers.stream().map(Users::getId)
                .collect(Collectors.toList()));

        teacher.getStudents().addAll(studentUsers);
        userRepository.save(teacher);

        return generateResponse(Collections.singletonList(createCommonUserResponse(teacher)));
    }



    @Override
    public Map<String, List<CommonV2Response>> getQuestionsByUser(Integer userId) {
        log.trace("Finding Questions for user {}",userId);
        List<Questions> questions = Optional.ofNullable(questionRepository
                .getQuestionsByUserId(userId)).orElse(ListUtils.EMPTY_LIST);
        log.debug("Finding Questions for user {} = {}",userId, questions.size());
        return createQuestionsResponse(questions);
    }

    private Map<String, List<CommonV2Response>> createQuestionsResponse(List<Questions> questions){
        List<CommonV2Response> commonV2Responses = questions.stream().map(question -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(question.getStringSid());
            commonV2Response.putField("question_id", String.valueOf(question.id));
            commonV2Response.putField("Text", question.getQuestion());
            commonV2Response.putField("Subject_id", String.valueOf(question.getSubject().id));
            commonV2Response.putField("year_group_id", String.valueOf(question.getYearGroupId().id));
            commonV2Response.putField("Difficulty_level", String.valueOf(question.getDifficulty()));
            commonV2Response.putField("category", String.valueOf(question.getCategory()));
            commonV2Response.putField("sub_category", String.valueOf(question.getSubCategory()));
            commonV2Response.putField("Correct_option", String.valueOf(question.getCorrectOption()));
            commonV2Response.putField("Learn", String.valueOf(question.getLearn()));
            commonV2Response.putField("Image_path", Optional.ofNullable(question.getImagePath()).orElse(StringUtils.EMPTY));

            return commonV2Response;
        }).collect(Collectors.toList());

        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String, List<CommonV2Response>> getQuestionsBySubcategories(List<String> subcategories) {
        log.trace("Finding Questions for subcategories {}",subcategories);
        List<Questions> questions = Optional.ofNullable(questionRepository
                .getQuestionsBySubCategoryIn(subcategories)).orElse(ListUtils.EMPTY_LIST);
        log.debug("Finding Questions for subcategories {} = {}",subcategories, questions.size());
        return createQuestionsResponse(questions);
    }
}

