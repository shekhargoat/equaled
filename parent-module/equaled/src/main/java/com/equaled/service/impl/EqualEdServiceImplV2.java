package com.equaled.service.impl;

import com.equaled.common.config.RestTemplateConfig;
import com.equaled.customrepository.impl.CustomRepositoyImpl;
import com.equaled.dozer.DozerUtils;
import com.equaled.entity.*;
import com.equaled.eserve.common.CommonUtils;
import com.equaled.eserve.common.JsonUtils;
import com.equaled.eserve.common.exception.ApplicationException;
import com.equaled.eserve.common.exception.IncorrectArgumentException;
import com.equaled.eserve.common.exception.RecordNotFoundException;
import com.equaled.eserve.exception.errorcode.ErrorCodes;
import com.equaled.repository.*;
import com.equaled.service.IEqualEdServiceV2;
import com.equaled.to.*;
import com.equaled.value.EqualEdEnums;
import com.equaled.value.UserType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@AllArgsConstructor
public class EqualEdServiceImplV2 implements IEqualEdServiceV2 {

    private final RestTemplateConfig restTemplateConfig;
    private final CustomRepositoyImpl customRepositoyImpl;
    private final DozerUtils dozerUtils;
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
    IExamScoreRepository examScoreRepository;
    IFRQuestionRepository frQuestionRepository;
    IFRQResponseRepository frResponseRepository;
    IStagingQuestionsRepository stagingQuestionsRepository;
    IUserProgressRepository userProgressRepository;
    LLMUsageRepository llmUsageRepository;
    UserPremiumStatusRepository userPremiumStatusRepository;
    private final SystemConfigRepository systemConfigRepository;

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
    public Map<String,List<CommonV2Response>> getSubjectByYearGroupId(Integer yearGroupId){
        log.trace("Finding Subject by yearGroupId: {}",yearGroupId);
        List<Subject> subjects = Optional.ofNullable(subjectCategoryRepository.findDistinctSubjectsByYearGroup(yearGroupId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Subject fetched for year {} = {}",yearGroupId, subjects.size());

        List<CommonV2Response> commonV2Responses = subjects.stream().map(subject -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(subject.getStringSid());
            commonV2Response.putField("subject", String.valueOf(subject.getName()));
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

        return getSetPractices(setpractices);
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
                    user.setFirstname(record.getFields().get("Firstname"));
                    user.setLastname(record.getFields().get("Lastname"));
                    user.setCountryCode(record.getFields().get("Countrycode"));
                    user.setStateCode(record.getFields().get("Statecode"));
                    user.setSchoolName(record.getFields().get("Schoolname"));
                    user.setDob(LocalDate.parse(record.getFields().get("Dob")));
                    user.setYearGroup(getYearGroup(Integer.parseInt(record.getFields().get("year_group_id"))));
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

    private YearGroup getYearGroup(int yearGroup) {
        return yearGroupRepository.findByYear(yearGroup).orElseThrow(()->
                new IncorrectArgumentException("Incorrect Year group Provided"));
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
        // finding sas and stanine data
        List<Map<String, Object>> examScores = getExamScore(examId);
        if(CollectionUtils.isNotEmpty(examScores)){
            return createImprovementResponse(improvements,examScores.get(0));
        }else
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
            commonV2Response.putField("category", Optional.ofNullable(answers.getQuestion().getCategory())
                    .orElse(answers.getQuestion().getSubject().getName()));
            commonV2Response.putField("sub_category", Optional.ofNullable(answers.getQuestion().getSubCategory()).orElse(StringUtils.EMPTY));
            commonV2Response.putField("Correct_option", answers.getQuestion().getCorrectOption());
            commonV2Response.putField("question_id", String.valueOf(answers.getQuestion().id));
            commonV2Response.putField("Explanation", answers.getExplanation());
            commonV2Response.putField("Time_Spent", String.valueOf(answers.getTimeSpent()));
            commonV2Response.putField("date", answers.getAnswerDate().toString());
            commonV2Response.putField("difficulty", Optional.ofNullable(String.valueOf(answers.getQuestion().getDifficulty())).orElse(StringUtils.EMPTY));
            commonV2Response.putField("User_option", Optional.ofNullable(answers.getUserOption()).orElse(StringUtils.EMPTY));
            commonV2Response.putField("Option_1_text",Optional.ofNullable(answers.getQuestion()).map(Questions::getOption1).orElse(StringUtils.EMPTY));
            commonV2Response.putField("Option_2_text",Optional.ofNullable(answers.getQuestion()).map(Questions::getOption2).orElse(StringUtils.EMPTY));
            commonV2Response.putField("Option_3_text",Optional.ofNullable(answers.getQuestion()).map(Questions::getOption3).orElse(StringUtils.EMPTY));
            commonV2Response.putField("Option_4_text",Optional.ofNullable(answers.getQuestion()).map(Questions::getOption4).orElse(StringUtils.EMPTY));
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

    private Map<String, List<CommonV2Response>> createImprovementResponse(List<Improvement> improvements,Map<String,Object> examscore) {
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
            commonV2Response.putField("sas",String.valueOf(examscore.get("sas_score")));
            commonV2Response.putField("stanineScore",String.valueOf(examscore.get("stanine_score")));

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
        commonV2Response.putField("year_group_id", String.valueOf(users.getYearGroup().getYear()));
        commonV2Response.putField("role", WordUtils.capitalizeFully(users.getRole().name().toLowerCase()));
        commonV2Response.putField("lastlogin", LocalDateTime.ofInstant(users.getLastLogin(),
                ZoneId.of("UTC")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        if(CollectionUtils.isNotEmpty(users.getStudents())){
            Set<Integer> studentIds = users.getStudents().stream().map(Users::getId).collect(Collectors.toSet());
            commonV2Response.putField("students",studentIds.toString());
        }
        if(CollectionUtils.isNotEmpty(users.getTeachers())){
            Set<Integer> studentIds = users.getTeachers().stream().map(Users::getId).collect(Collectors.toSet());
            commonV2Response.putField("teachers",studentIds.toString());
        }
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

        return getSetPractices(setpractices);
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
    public Map<String, List<CommonV2Response>> getCategoriesBySubAndYearGroup(String subjectName, Integer yearGroup) {
        log.trace("Finding Categories for subject {} and year group {}", subjectName, yearGroup);
        List<SubjectCategories> categories = Optional.ofNullable(subjectCategoryRepository
                .getSubjectCategoriesBySubjectAndYearGroupId(subjectName, yearGroup)).orElse(ListUtils.EMPTY_LIST);
        log.debug("Fetched Categories for subject {} and year group {} = {}", subjectName, yearGroup, categories.size());
        List<CommonV2Response> commonV2Responses = categories.stream()
                .sorted(Comparator.comparingInt(SubjectCategories::getSortOrder)).map(category -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            Map<String, Object> subCategoryMap = new LinkedHashMap<>();
            subCategoryMap.put("name", category.getSubCategory());

            List<String> subCategoryDetails = Arrays.asList(category.getSubCategory1().split("\n"));
            subCategoryMap.put("sub_category_1", subCategoryDetails);
            commonV2Response.putField("sub_category", subCategoryMap);
            return commonV2Response;
        }).collect(Collectors.toList());
        return generateResponse(commonV2Responses);
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
            /* In the html page the value for the dropdowns are being hardcoded and generally the value is equal to the
             Year so for 11 it is being put as 11 whereas the actual id will defer in the database
             Hence we are getting the year group by Year rather than ID*/
            YearGroup yearGroup = yearGroupRepository.findByYear(MapUtils.getIntValue(setpractice.getFields(), "year_group_id"))
                            .orElseThrow(()-> new IncorrectArgumentException("Invalid Year Group"));
            setpractice1.setYearGroup(yearGroup);

            setpractice1.setStatus(EqualEdEnums.SetpracticeStatus.PENDING);
            setpractice1.setQuestions(MapUtils.getString(setpractice.getFields(), "questions"));
            setpractice1.setPracticeName(MapUtils.getString(setpractice.getFields(), "practicename"));
            setpractice1.setNoOfQ(MapUtils.getInteger(setpractice.getFields(), "no_questions"));
            setpractice1.setTimeLimit(MapUtils.getInteger(setpractice.getFields(), "time_limit"));

            setpractices.add(setpractice1);
        });

        if(CollectionUtils.isNotEmpty(setpractices)){
            List<Setpractice> setpractices1 = setPracticeRepository.saveAll(setpractices);
            return getSetPractices(setpractices1);

        }else return null;
    }

    private Map<String, List<CommonV2Response>> getSetPractices(List<Setpractice> setpractices1) {
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
            commonV2Response.putField("Subject_id", Optional.ofNullable(setpractice.getSubject())
                    .map(Subject::getId).map(String::valueOf).orElse(StringUtils.EMPTY));
            commonV2Response.putField("year_group_id", Optional.ofNullable(setpractice.getYearGroup())
                    .map(YearGroup::getYear).map(String::valueOf).orElse(StringUtils.EMPTY));

            return commonV2Response;
        }).collect(Collectors.toList());
        return generateResponse(commonV2Responses);
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

    @Override
    public UserAnswerAITO submitUserAnswerAI(UserAnswerAITO userAnswerAITO) {
        if(ObjectUtils.isEmpty(userAnswerAITO) || ObjectUtils.isEmpty(userAnswerAITO.getAnswers()))
            throw new IncorrectArgumentException("Answer information is not available");
        if(log.isTraceEnabled())
            log.trace("Submitting user answers AI : {}",CommonUtils.toJsonFunction.apply(userAnswerAITO));
        else
            log.debug("Submitting user answers AI : {}",userAnswerAITO);
        // creating the exam_score record
        Users user = userRepository.findById(userAnswerAITO.getUserId())
                .orElseThrow(() -> new IncorrectArgumentException("Invalid User Id"));
        ExamScore examScore = createExamScore(userAnswerAITO, user);
        userAnswerAITO.getAnswers()
                .forEach(userAnswersTO -> {
                    UserAnswers userAnswers = new UserAnswers();
                    userAnswers.setSid(BaseEntity.generateByteUuid());
                    userAnswers.setUser(user);
                    Questions orCreateQuestion = getOrCreateQuestion(userAnswersTO.getQuestion());
                    userAnswersTO.getQuestion().setSid(orCreateQuestion.getStringSid());
                    userAnswers.setQuestion(orCreateQuestion);
                    userAnswers.setExamScore(examScore);
                    userAnswers.setExamId(userAnswerAITO.getExamId());
                    // get user answer date
                    userAnswers.setAnswerDate(Instant.now());
                    userAnswers.setTimeSpent(userAnswersTO.getTimeSpent());
                    userAnswers.setExplanation(userAnswersTO.getExplanation());
                    userAnswers.setUserOption(userAnswersTO.getUserOption());
                    userAnswers.setCorrectOption(userAnswersTO.getCorrectOption().isEmpty() ? "a" : userAnswersTO.getCorrectOption());
                    log.trace("Submitting answer");
                    UserAnswers userAnswers1 = useranswerRepository.save(userAnswers);
                    log.debug("Users answer created : {}", userAnswers.getSid());
                    userAnswersTO.setSid(userAnswers1.getStringSid());
                });
        return userAnswerAITO;
    }

    @Override
    public List<Map<String,Object>> getExamScore(String examId) {
        if(CommonUtils.isEmpty(examId)) throw new ApplicationException("Invalid exam score Id");
        log.trace("Finding exam scores for exam Id {}",examId);
        List<ExamScore> examScores = Optional.ofNullable(examScoreRepository.getExamScoreByExamId(examId)).orElse(ListUtils.EMPTY_LIST);
        log.debug("Found {} exam scores for exam Id {}",examScores.size(), examId);

        return examScores.stream().map(examScore ->
            JsonUtils.convertStringToMap(examScore.getExamScore())
        ).collect(Collectors.toList());
    }

    private ExamScore createExamScore(UserAnswerAITO userAnswerAITO, Users user) {
        ExamScore examScore = new ExamScore();
        examScore.setSid(BaseEntity.generateByteUuid());
        examScore.setUser(user);
        examScore.setExamId(userAnswerAITO.getExamId());
        examScore.setCreatedOn(Instant.now());
        examScore.setExamScore(userAnswerAITO.getExamScore());
        return examScoreRepository.save(examScore);
    }

    private Questions getOrCreateQuestion(QuestionsTO questionsTO) {
        if(StringUtils.isEmpty(questionsTO.getQuestionAiId()))throw new IncorrectArgumentException("AI Question ID is not available.");
        Questions question = questionRepository.findQuestionsByQuestionAiId(questionsTO.getQuestionAiId()).orElse(null);
        if(ObjectUtils.isEmpty(question)){
            Questions questions = new Questions();
            questions.setSid(BaseEntity.generateByteUuid());
            questions.setQuestion(questionsTO.getQuestion());
            questions.setOption1(questionsTO.getOption1());
            questions.setOption2(questionsTO.getOption2());
            questions.setOption3(questionsTO.getOption3());
            questions.setOption4(questionsTO.getOption4());
            questions.setCorrectOption(questionsTO.getCorrectOption());
            questions.setImagePath(questionsTO.getImagePath());
            questions.setQuestionAiId(questionsTO.getQuestionAiId());
            questions.setSubject(subjectRepository.findById(questionsTO.getSubjectId()).orElseThrow(()-> new IncorrectArgumentException("Invalid Subject Id")));
            questions.setYearGroupId(yearGroupRepository.findByYear(questionsTO.getYear_group_id()).orElseThrow(()-> new IncorrectArgumentException("Invalid Year Group Id")));
            questions.setDifficulty(questionsTO.getDifficulty());
            questions.setCategory(questionsTO.getCategory());
            questions.setSubCategory(questionsTO.getSubCategory());
            Questions saved = questionRepository.save(questions);
            log.trace("Questions not found by question {} create new question for questionAiID {}",saved.getQuestion(),saved.getQuestionAiId());
            return saved;
        }else{
            return question;
        }
    }

    @Override
    public Map<String, List<CommonV2Response>> createProfile(CreateProfileRequest request, Integer guardianProfile) {
        Users guardian = userRepository.findById(guardianProfile).orElseThrow(()-> new IncorrectArgumentException("Invalid Guardian Id"));
        AtomicReference<Accounts> accounts = new AtomicReference<>();

        List<Users> users = request.getRecords().stream()
                .map(record -> {
                    Users user = new Users();
                    user.setSid(BaseEntity.generateByteUuid());
                    user.setUsername(record.getFields().get("Username"));
                    user.setEmail(record.getFields().get("Email"));
                    user.setPassword(record.getFields().get("Password"));
                    user.setYearGroup(getYearGroup(Integer.parseInt(record.getFields().get("year_group_id"))));
                    if(accounts.get() == null){
                        accounts.set(Optional.ofNullable(record.getFields().get("account_id")).filter(StringUtils::isNumeric)
                                .map(Integer::parseInt).map(ids -> getOrCreateAccount(ids, record.getFields().get("Username")))
                                .orElse(accountRepository.findById(1).get()));
                    }
                    user.setRelatedAccount(accounts.get());
                    user.setRole(EqualEdEnums.UserRole.valueOf(record.getFields().get("role").toUpperCase()));
                    user.setLastLogin(Instant.now());
                    user.setLastUpdatedOn(Instant.now());
                    return user;
                }).collect(Collectors.toList());

        List<Users> usersCreated = userRepository.saveAll(users);
//        guardian.setStudents(new HashSet<>(usersCreated));
        // adding the new student to the existing students list
        guardian.getStudents().addAll(usersCreated);
        // saving into teacher_has_students table
        userRepository.save(guardian);
        List<CommonV2Response> commonV2Responses = usersCreated.stream()
                .map(EqualEdServiceImplV2::createCommonUserResponse).collect(Collectors.toList());
        return generateResponse(commonV2Responses);
    }

    @Override
    public Integer getSubjectIdByName(String subjectName){
        return Optional.ofNullable(subjectName).filter(StringUtils::isNotEmpty).map(subjectRepository::findByName)
                .filter(CollectionUtils::isNotEmpty).map(subjects -> subjects.get(0)).map(Subject::getId)
                .orElseThrow(() -> new RecordNotFoundException(ErrorCodes.U001,"No Subject Found for "+subjectName));
    }

    @Override
    public CommonV2Response createFRQuestion(Map<String, String> frquestion){

        log.trace("Creating FRQuestion for data: {}",frquestion);
        if(MapUtils.isEmpty(frquestion))throw new IncorrectArgumentException("Empty records to enter");
        Integer subjectId = Optional.ofNullable(frquestion.get("Subject_id")).map(Integer::parseInt).orElseThrow(()->new IncorrectArgumentException("Subject ID must be provided"));
        Integer createBy = Optional.ofNullable(frquestion.get("CreatedBy")).map(Integer::parseInt).orElseThrow(()->new IncorrectArgumentException("Created By ID must be provided"));
        String difficulty = MapUtils.getString(frquestion, "DifficultyLevel", "Difficult");

        FRQuestion frQuestion = new FRQuestion();
        frQuestion.generateUuid();
        frQuestion.setText(MapUtils.getString(frquestion, "QuestionText", ""));
        frQuestion.setDifficulty(difficulty);
        frQuestion.setCreationDate(Instant.now());

        frQuestion.setCreatedBy(Optional.ofNullable(createBy).flatMap(userRepository::findById).orElseThrow(()->new IncorrectArgumentException("Invalid user Id")));
        frQuestion.setSubject(Optional.ofNullable(subjectId).flatMap(subjectRepository::findById).orElseThrow(()->new IncorrectArgumentException("Invalid Subject Id")));

        frQuestionRepository.save(frQuestion);

        CommonV2Response commonV2Response = new CommonV2Response();
        commonV2Response.setId(frQuestion.getStringSid());
        commonV2Response.putField("QuestionText",frQuestion.getText());
        commonV2Response.putField("Difficulty",frQuestion.getDifficulty());
        commonV2Response.putField("CreationDate",frQuestion.getCreationDate().toString());

        return commonV2Response;

    }

    @Override
    public void createFRQResponse(CreateFRQResponseRequest createFRQResponseRequest){

        log.trace("Creating FRQResponses for payload : {}",createFRQResponseRequest);

        List<CommonV2Request> incomingRecords = createFRQResponseRequest.getRecords();
        List<FRQResponse> responsesToSave = new ArrayList<>();

        for (CommonV2Request record : incomingRecords) {
            Map<String, String> incomingFields = record.getFields();
            FRQResponse frqResponse = new FRQResponse();
            frqResponse.generateUuid();
            log.trace("Inserting FRQResponse for data: {}",incomingFields);
            try{
                FRQuestion frQuestion= Optional.ofNullable(incomingFields.get("Question_id"))
                        .flatMap(frQuestionRepository::findBySid)
                        .orElseThrow(()-> new IncorrectArgumentException("Question sid is not valid"));

                Users user = Optional.ofNullable(incomingFields.get("User_id")).map(Integer::parseInt)
                        .flatMap(userRepository::findById)
                        .orElseThrow(()->new IncorrectArgumentException("User id is not valid"));
                frqResponse.setQuestion(frQuestion);
                frqResponse.setUser(user);
                responsesToSave.add(frqResponse);
            }catch (IncorrectArgumentException ie){
                log.error("Error while converting FRQResponses: {}",ie.getMessage());
            }
        }

        //saving all the data together
        frResponseRepository.saveAll(responsesToSave);
        frResponseRepository.flush();
    }

    @Override
    public void updateFRQResponse(Map<String, String> fields, String responseSid) {
        log.trace("Updating FRQResponses for Id : {}",responseSid);
        log.trace("Updating payload : {}",fields);

        FRQResponse frqResponse = Optional.ofNullable(responseSid).filter(StringUtils::isNotEmpty)
                .flatMap(frResponseRepository::findBySid)
                .orElseThrow(()->new IncorrectArgumentException("Invalid responseSid"));


        frqResponse.setText(MapUtils.getString(fields, "ResponseText",""));
        frqResponse.setGrade(MapUtils.getString(fields, "Grade",""));
        frqResponse.setStrengths(MapUtils.getString(fields, "Strengths",""));
        frqResponse.setImprovement(MapUtils.getString(fields, "Improvement",""));
        frqResponse.setSectionMarks(MapUtils.getString(fields, "section_marks",""));
        frqResponse.setStatus(MapUtils.getString(fields, "Status",""));
        frqResponse.setSubmissionDate(Instant.now());

        frResponseRepository.save(frqResponse);

        log.debug("FrqResponse updated for : {}",responseSid);

    }

    @Override
    public Map<String, List<CommonV2Response>> getFRQResponsesByStatusAndUser(String status, Integer userId) {
        List<FRQResponse> frqResponses = frResponseRepository.findByUserIdAndStatus(userId, status);
        List<CommonV2Response> convertedResponse = frqResponses.stream()
                .map(EqualEdServiceImplV2::createCommonFRQresponses).collect(Collectors.toList());
        return generateResponse(convertedResponse);
    }

    @Override
    public CommonV2Response getFRQResponseBySid(String responseSid) {
        FRQResponse frqResponse = Optional.ofNullable(responseSid).filter(StringUtils::isNotEmpty)
                .flatMap(frResponseRepository::findBySid)
                .orElseThrow(()->new IncorrectArgumentException("Invalid responseSid"));

        return createCommonFRQresponses(frqResponse);
    }

    private static CommonV2Response createCommonFRQresponses(FRQResponse frqResponse) {
        CommonV2Response commonV2Response = new CommonV2Response();
        commonV2Response.setId(frqResponse.getStringSid());
        commonV2Response.setCreatedTime(Optional.ofNullable(frqResponse.getSubmissionDate())
                        .map(instant -> LocalDateTime.ofInstant(instant,ZoneId.systemDefault()))
                .map(ldt -> ldt.format(DateTimeFormatter.ISO_DATE_TIME))
                .orElse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));
        commonV2Response.putField("QuestionText", frqResponse.getQuestion().getText());
        commonV2Response.putField("ResponseText", frqResponse.getText());
        commonV2Response.putField("Grade", frqResponse.getGrade());
        commonV2Response.putField("Strengths", frqResponse.getStrengths());
        commonV2Response.putField("Improvement", frqResponse.getImprovement());
        commonV2Response.putField("section_marks", frqResponse.getSectionMarks());
        commonV2Response.putField("Status", frqResponse.getStatus());
        commonV2Response.putField("DifficultyLevel",frqResponse.getQuestion().getDifficulty());
        commonV2Response.putField("Time_limit",String.valueOf(frqResponse.getQuestion().getTimeLimit()));
        return commonV2Response;
    }

    @Override
    public Optional<String> createStagingQuestion(StagingQuestionsTO stagingQuestionsTO) {
        StagingQuestions stagingQuestion=new StagingQuestions();

        stagingQuestion.setImage_path(stagingQuestionsTO.getImage_path());
        stagingQuestion.setDifficult_level(stagingQuestionsTO.getDifficult_level());
        stagingQuestion.setCategory(stagingQuestionsTO.getCategory());
        stagingQuestion.setSub_category(stagingQuestionsTO.getSub_category());
        stagingQuestion.setText(stagingQuestionsTO.getText());
        stagingQuestion.setOption_1_text(stagingQuestionsTO.getOption_1_text());
        stagingQuestion.setOption_2_text(stagingQuestionsTO.getOption_2_text());
        stagingQuestion.setOption_3_text(stagingQuestionsTO.getOption_3_text());
        stagingQuestion.setOption_4_text(stagingQuestion.getOption_4_text());
        stagingQuestion.setCorrect_option(stagingQuestionsTO.getCorrect_option());
        stagingQuestion.setExplanation(stagingQuestionsTO.getExplanation());
        stagingQuestion.setSub_category_1(stagingQuestionsTO.getSub_category_1());
        stagingQuestion.setSub_category_2(stagingQuestionsTO.getSub_category_2());
        YearGroup yearGroup = yearGroupRepository.findByYear(stagingQuestionsTO.getYear_group_id())
                .orElseThrow(()-> new IncorrectArgumentException("Invalid Year Group"));
        Subject subject = subjectRepository.findById(stagingQuestionsTO.getSubject_id())
                .orElseThrow(()-> new IncorrectArgumentException("Invalid Subject Id"));
        stagingQuestion.setYearGroup(yearGroup);
        stagingQuestion.setSubject(subject);
        stagingQuestionsRepository.save(stagingQuestion);
        return Optional.of(String.valueOf(stagingQuestion.getId()));
    }

    public void saveSubjectData(LinkedHashMap<String, Object> payload) {
        Map<String, Object> metadata = MapUtils.getMap(payload, "metadata");
        String subjectName = MapUtils.getString(metadata, "subject");
        String yearGroupIdStr = MapUtils.getString(metadata, "year_group_id");
        String countryId = MapUtils.getString(metadata, "country_id");
        String curriculum = MapUtils.getString(metadata, "curriculum");
        String state = MapUtils.getString(metadata, "state");
        String language = MapUtils.getString(metadata, "language");
        String notes = MapUtils.getString(metadata, "notes");
        // Find or create subject
        List<Subject> subjects = subjectRepository.findByName(subjectName);
        Subject subject;
        if (!subjects.isEmpty()) {
            log.info("Subject found: {}", subjectName);
            subject = subjects.get(0);
        } else {
            log.info("Creating new Subject: {}",subjectName);
            Subject newSubject = new Subject();
            newSubject.setName(subjectName);
            newSubject.setSid(BaseEntity.generateByteUuid());
            newSubject.setCreatedOn(Instant.now());
            newSubject.setLastUpdatedOn(Instant.now());
            subject = subjectRepository.save(newSubject);
        }
        int yearGroupId = (int) Long.parseLong(yearGroupIdStr);
        // Find or create YearGroup
        YearGroup yearGroup;
        Optional<YearGroup> optionalYearGroup = yearGroupRepository.findByYear(yearGroupId);
        if (optionalYearGroup.isPresent()) {
            log.info("YearGroup found: {}", yearGroupId);
            yearGroup = optionalYearGroup.get();
        } else {
            log.info("Creating new YearGroup: {}", yearGroupId);
            YearGroup newYearGroup = new YearGroup();
            newYearGroup.setYear(yearGroupId);
            newYearGroup.setSid(BaseEntity.generateByteUuid());
            yearGroup = yearGroupRepository.save(newYearGroup);
        }
        Map<String, Object> jsonResponse = MapUtils.getMap(payload, "json_response");
        List<Map<String, Object>> categories = (List<Map<String, Object>>) jsonResponse.get("categories");
        int sortOrder = 1;
        for (Map<String, Object> category : categories) {
            String subCategory = MapUtils.getString(category, "name");
            List<String> subcategories = (List<String>) category.get("subcategories");
            String joinedSubcategories = subcategories.stream()
                    .map(this::formatSubcategory).collect(Collectors.joining(""));
            log.info("Saving subject categories data: {}",sortOrder);
            SubjectCategories categoryEntity = new SubjectCategories();
            categoryEntity.setSid(BaseEntity.generateByteUuid());
            categoryEntity.setSubject(subject);
            categoryEntity.setYearGroup(yearGroup);
            categoryEntity.setCountryId(countryId);
            categoryEntity.setCurriculum(curriculum);
            categoryEntity.setState(state);
            categoryEntity.setNotes(notes);
            categoryEntity.setLanguage(language);
            categoryEntity.setSortOrder(sortOrder++);
            categoryEntity.setSubCategory(subCategory);
            categoryEntity.setSubCategory1(joinedSubcategories);
            subjectCategoryRepository.save(categoryEntity);
            log.info("Subject data saved successfully.");
        }
    }

    private String formatSubcategory(String input) {
        // If input contains any non-alphanumeric character, skip formatting
        if (!input.matches("^[a-zA-Z0-9]*$")) {
            return input + "\n";
        }
        // Otherwise format to spaced text
        return Arrays.stream(input.split("(?=[A-Z])"))
                .map(String::trim)
                .collect(Collectors.joining(" ")) + "\n";
    }

    @Override
    public Map<String, List<CommonV2Response>> getWeeklySubmissionsByYearGroupId(Integer yearGroupId) {
        log.trace("Finding weekly submissions by yearGroupId: {}", yearGroupId);

        LocalDateTime now = LocalDateTime.now();
        int dayOfWeek = now.getDayOfWeek().getValue();
        int daysSinceSunday = dayOfWeek % 7;

        LocalDateTime startOfWeekLocal = now.minusDays(daysSinceSunday).with(LocalTime.MIN);
        LocalDateTime endOfWeekLocal = startOfWeekLocal.plusDays(6).with(LocalTime.MAX);

        ZonedDateTime startOfWeekUTC = startOfWeekLocal.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime endOfWeekUTC = endOfWeekLocal.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);

        Instant startTime = startOfWeekUTC.toInstant();
        Instant endTime = endOfWeekUTC.toInstant();

        List<Object[]> records = useranswerRepository.findWeeklyUserDifficultiesNative(yearGroupId, startTime, endTime);
        log.info("Found: {} responses for yearGroup: {}", records.size(), yearGroupId);

        // Grouping: userId -> examId -> list of answers
        Map<String, Map<String, List<Map<String, String>>>> groupedByUser = new HashMap<>();

        for (Object[] row : records) {
            String userId = row[0] != null ? row[0].toString() : "";
            String difficulty = row[1] != null ? row[1].toString() : "";
            String userOption = row[2] != null ? row[2].toString() : "";
            String correctOption = row[3] != null ? row[3].toString() : "";
            String examId = row[4] != null ? row[4].toString() : "";

            Map<String, String> answerData = new HashMap<>();
            answerData.put("difficulty", difficulty);
            answerData.put("userOption", userOption);
            answerData.put("correctOption", correctOption);

            groupedByUser
                    .computeIfAbsent(userId, k -> new HashMap<>())
                    .computeIfAbsent(examId, k -> new ArrayList<>())
                    .add(answerData);
        }

        List<CommonV2Response> responses = groupedByUser.entrySet().stream().map(userEntry -> {
            String userId = userEntry.getKey();
            Collection<List<Map<String, String>>> submissionsPerExam = userEntry.getValue().values();

            CommonV2Response response = new CommonV2Response();
            response.setId(userId);
            response.putField("submissions", new ArrayList<>(submissionsPerExam));
            return response;
        }).collect(Collectors.toList());

        return generateResponse(responses);
    }

    @Override
    public Map<String, List<CommonV2Response>> getNameById(Integer id) {
        log.trace("Finding user by id: {}", id);
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ErrorCodes.U001, "User not found for given id: " + id));
        String fullName = Stream.of(user.getFirstname(), user.getLastname())
                .filter(name -> name != null && !name.trim().isEmpty())
                .collect(Collectors.joining(" "));
        if (fullName.isEmpty()) {
            log.warn("Name is empty for user: {}, adding username", user.getId());
            fullName = user.getUsername();
        }
        CommonV2Response response = new CommonV2Response();
        response.putField("userName", fullName);
        return generateResponse(Collections.singletonList(response));
    }

    @Override
    public CommonV2Response createUserProgress(Map<String, String> fields) {
        if (fields == null || fields.isEmpty()) {
            throw new IllegalArgumentException("Request fields cannot be null or empty");
        }
        try {
            String userIdStr = fields.get("user_id");
            String completedStr = fields.get("completed");
            if (userIdStr == null || userIdStr.isEmpty()) {
                throw new IllegalArgumentException("user_id is required");
            }
            if (completedStr == null || completedStr.isEmpty()) {
                throw new IllegalArgumentException("completed field is required");
            }
            int userId = Integer.parseInt(userIdStr);
            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
            UserProgress progress = new UserProgress();
            progress.setUser(user);
            progress.setCompleted(Boolean.parseBoolean(completedStr));
            progress.setSubject(fields.getOrDefault("subject", ""));
            progress.setCategory(fields.getOrDefault("category", ""));
            progress.setCompletedAt(parseDateTime(fields.get("completed_at")));
            progress.setScore(parseInteger(fields.get("score")));
            progress.setTotalQuestions(parseInteger(fields.get("total_questions")));
            progress.setPercentage(parseInteger(fields.get("percentage")));
            UserProgress saved = userProgressRepository.save(progress);
            log.info("User progress saved successfully with id {} for userId {}", saved.getId(), user.getId());
            CommonV2Response response = new CommonV2Response();
            response.setId(String.valueOf(saved.getId()));
            response.putField("message", "User progress saved successfully.");
            response.putField("user_id", userIdStr);
            return response;
        } catch (IllegalArgumentException e) {
            log.warn("Validation error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Failed to save user progress", e);
            throw new RuntimeException("Failed to save user progress: " + e.getMessage(), e);
        }
    }

    private Integer parseInteger(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            log.warn("Invalid integer value: {}", value);
            return null;
        }
    }

    private LocalDateTime parseDateTime(String value) {
        try {
            return (value != null && !value.isEmpty()) ? LocalDateTime.parse(value) : null;
        } catch (Exception e) {
            log.warn("Invalid datetime format: {}", value);
            return null;
        }
    }

    @Override
    public CommonV2Response createUserProgressBulk(Map<String, String> fields) {
        if (fields == null || !fields.containsKey("progress_items")) {
            throw new IllegalArgumentException("Missing 'progress_items' field in request.");
        }
        try {
            String progressItemsJson = fields.get("progress_items");
            List<Map<String, Object>> progressItems = new ObjectMapper().readValue(
                    progressItemsJson,
                    new TypeReference<List<Map<String, Object>>>() {}
            );
            int savedCount = 0;
            List<String> failedItems = new ArrayList<>();
            for (Map<String, Object> item : progressItems) {
                try {
                    String userIdStr = String.valueOf(item.get("user_id"));
                    String completedStr = String.valueOf(item.get("completed"));
                    if (userIdStr == null || userIdStr.isEmpty()) {
                        throw new IllegalArgumentException("user_id is required");
                    }
                    if (completedStr == null || completedStr.isEmpty()) {
                        throw new IllegalArgumentException("completed field is required");
                    }
                    int userId = Integer.parseInt(userIdStr);
                    Users user = userRepository.findById(userId)
                            .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
                    UserProgress progress = new UserProgress();
                    progress.setUser(user);
                    progress.setCompleted(Boolean.parseBoolean(completedStr));
                    progress.setSubject(String.valueOf(item.getOrDefault("subject", "")));
                    progress.setCategory(String.valueOf(item.getOrDefault("category", "")));
                    progress.setCompletedAt(parseDateTimeFromObject(item.get("completed_at")));
                    progress.setScore(parseIntegerFromObject(item.get("score")));
                    progress.setTotalQuestions(parseIntegerFromObject(item.get("total_questions")));
                    progress.setPercentage(parseIntegerFromObject(item.get("percentage")));
                    userProgressRepository.save(progress);
                    savedCount++;
                } catch (Exception e) {
                    log.warn("Failed to save progress item: {}", e.getMessage());
                    failedItems.add(e.getMessage());
                }
            }
            CommonV2Response response = new CommonV2Response();
            response.putField("saved_count", String.valueOf(savedCount));
            response.putField("failed_count", String.valueOf(failedItems.size()));
            response.putField("message", "Bulk user progress saved successfully.");
            if (!failedItems.isEmpty()) {
                response.putField("errors", failedItems.toString());
            }
            return response;
        } catch (Exception e) {
            log.error("Error processing bulk user progress", e);
            throw new RuntimeException("Failed to process bulk user progress: " + e.getMessage(), e);
        }
    }

    private Integer parseIntegerFromObject(Object value) {
        try {
            return (value != null && !value.toString().isEmpty()) ? Integer.parseInt(value.toString()) : null;
        } catch (NumberFormatException e) {
            log.warn("Invalid integer value: {}", value);
            return null;
        }
    }

    private LocalDateTime parseDateTimeFromObject(Object value) {
        try {
            return (value != null && !value.toString().isEmpty()) ? LocalDateTime.parse(value.toString()) : null;
        } catch (Exception e) {
            log.warn("Invalid datetime format: {}", value);
            return null;
        }
    }

    @Override
    public Map<String, Object> getUserProgress(int userId, String subject) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        List<UserProgress> progressList = (subject != null && !subject.isEmpty())
                ? userProgressRepository.findByUserIdAndSubject(userId, subject)
                : userProgressRepository.findByUserId(userId);
        List<Map<String, Object>> progressData = new ArrayList<>();
        int completedTopics = 0;

        for (UserProgress p : progressList) {
            boolean isCompleted = Boolean.TRUE.equals(p.getCompleted());
            if (isCompleted) completedTopics++;

            Map<String, Object> entry = new HashMap<>();
            entry.put("user_id", userId);
            entry.put("subject", p.getSubject());
            entry.put("category", p.getCategory());
            entry.put("completed", isCompleted);
            entry.put("completed_at", p.getCompletedAt());
            entry.put("score", p.getScore());
            entry.put("total_questions", p.getTotalQuestions());
            entry.put("percentage", p.getPercentage() != null ? p.getPercentage().doubleValue() : 0.0);
            progressData.add(entry);
        }

        int totalTopics = progressList.size();
        double completionPercentage = totalTopics > 0
                ? Math.round((completedTopics * 10000.0 / totalTopics)) / 100.0 : 0.0;

        Map<String, Object> summary = new HashMap<>();
        summary.put("total_topics", totalTopics);
        summary.put("completed_topics", completedTopics);
        summary.put("overall_completion", completionPercentage);
        summary.put("badges_earned", completedTopics);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("progress", progressData);
        response.put("summary", summary);
        return response;
    }

    @Override
    public CommonV2Response saveLLMUsageAndPremiumStatus(LLMUsageWrapperDTO wrapperDTO) {
        try {
            if (wrapperDTO == null) {
                throw new IllegalArgumentException("Request body is null");
            }
            if (wrapperDTO.getLlm_usage() != null) {
                for (LLMUsageDTO dto : wrapperDTO.getLlm_usage()) {
                    if (dto.getUser_id() == null || dto.getWeek_start() == null || dto.getWeek_end() == null) continue;

                    LLMUsage usage = new LLMUsage();
                    usage.setUserId(dto.getUser_id());
                    usage.setWeekStart(parseDateTimeFromObject(dto.getWeek_start()));
                    usage.setWeekEnd(parseDateTimeFromObject(dto.getWeek_end()));
                    usage.setCallCount(dto.getCall_count() != null ? dto.getCall_count() : 0);
                    usage.setIsPremium(Boolean.TRUE.equals(dto.getIs_premium()));
                    usage.setUserType(Optional.ofNullable(dto.getUser_type()).orElse("free"));
                    usage.setCreatedAt(parseDateTimeFromObject(dto.getCreated_at()));
                    usage.setUpdatedAt(Optional.ofNullable(parseDateTimeFromObject(dto.getUpdated_at())).orElse(LocalDateTime.now()));
                    llmUsageRepository.save(usage);
                }
            }
            if (wrapperDTO.getUser_premium_status() != null) {
                for (UserPremiumStatusDTO dto : wrapperDTO.getUser_premium_status()) {
                    if (dto.getUser_id() == null) continue;

                    UserPremiumStatus status = new UserPremiumStatus();
                    status.setUserId(dto.getUser_id());
                    status.setIsPremium(Boolean.TRUE.equals(dto.getIs_premium()));
                    status.setUserType(Optional.ofNullable(dto.getUser_type()).orElse("free"));
                    status.setPremiumStartDate(parseDateTimeFromObject(dto.getPremium_start_date()));
                    status.setPremiumEndDate(parseDateTimeFromObject(dto.getPremium_end_date()));
                    status.setSubscriptionType(Optional.ofNullable(dto.getSubscription_type()).orElse("free"));
                    status.setCreatedAt(parseDateTimeFromObject(dto.getCreated_at()));
                    status.setUpdatedAt(Optional.ofNullable(parseDateTimeFromObject(dto.getUpdated_at())).orElse(LocalDateTime.now()));
                    userPremiumStatusRepository.save(status);
                }
            }
            CommonV2Response res = new CommonV2Response();
            res.putField("message", "LLM usage and premium status saved successfully.");
            return res;
        } catch (Exception e) {
            log.error("Failed to save LLM usage and premium status", e);
            throw new RuntimeException("Error saving data: " + e.getMessage(), e);
        }
    }

    @Override
    public LLMUsageSummaryResponse getLLMUsageSummary(String userId) {
        LocalDateTime now = LocalDateTime.now();
        int dayOfWeek = now.getDayOfWeek().getValue();
        int daysSinceMonday = dayOfWeek - DayOfWeek.MONDAY.getValue();
        LocalDateTime startOfWeekLocal = now.minusDays(daysSinceMonday).with(LocalTime.MIN);
        LocalDateTime endOfWeekLocal = startOfWeekLocal.plusDays(6).with(LocalTime.MAX);

        UserPremiumStatus status = userPremiumStatusRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserPremiumStatus s = new UserPremiumStatus();
                    s.setUserId(userId);
                    s.setIsPremium(false);
                    s.setUserType(UserType.FREE.getType());
                    s.setSubscriptionType(UserType.FREE.getType());
                    return s;
                });
        LLMUsage usage = llmUsageRepository.findByUserIdAndWeekStartGreaterThanEqualAndWeekEndLessThanEqual(
                        userId, startOfWeekLocal, endOfWeekLocal)
                .orElseGet(() -> {
                    LLMUsage u = new LLMUsage();
                    u.setUserId(userId);
                    u.setWeekStart(startOfWeekLocal);
                    u.setWeekEnd(endOfWeekLocal);
                    u.setCallCount(0);
                    return u;
                });

        UserType userTypeEnum = UserType.fromString(status.getUserType());
        int currentUsage = usage.getCallCount();
        int limit = userTypeEnum.getLimit();
        int warningThreshold = userTypeEnum.getWarningThreshold();
        int criticalThreshold = userTypeEnum.getCriticalThreshold();
        boolean isUnlimited = userTypeEnum.isUnlimited();

        int remaining = isUnlimited ? -1 : Math.max(0, limit - currentUsage);
        boolean limitReached = !isUnlimited && currentUsage >= limit;
        double percentageUsed = isUnlimited ? 0.0 : (currentUsage * 100.0 / limit);
        boolean isWarning = !isUnlimited && currentUsage >= warningThreshold;
        boolean isCritical = !isUnlimited && currentUsage >= criticalThreshold;
        long daysUntilReset = ChronoUnit.DAYS.between(now.toLocalDate(), endOfWeekLocal.toLocalDate().plusDays(1));

        return LLMUsageSummaryResponse.builder()
                .current_usage(currentUsage)
                .limit(isUnlimited ? "unlimited" : limit)
                .remaining(isUnlimited ? "unlimited" : remaining)
                .limit_reached(limitReached)
                .percentage_used(percentageUsed)
                .user_type(status.getUserType())
                .is_premium(status.getIsPremium())
                .is_unlimited(isUnlimited)
                .subscription_type(status.getSubscriptionType())
                .warning_threshold(warningThreshold)
                .critical_threshold(criticalThreshold)
                .is_warning(isWarning)
                .is_critical(isCritical)
                .week_start(startOfWeekLocal)
                .week_end(endOfWeekLocal)
                .days_until_reset((int) daysUntilReset)
                .build();
    }

    @Override
    @Transactional
    public String updateLLMUsageAndPremiumStatus(LLMUsageWrapperDTO wrapperDTO) {
        if (wrapperDTO == null) {
            log.warn("Wrapper DTO is null, skipping update.");
            return "No data to update.";
        }
        AtomicInteger llmUpdatedCount = new AtomicInteger();
        AtomicInteger premiumUpdatedCount = new AtomicInteger();
        List<LLMUsageDTO> llmList = wrapperDTO.getLlm_usage();
        if (llmList != null) {
            for (LLMUsageDTO dto : llmList) {
                String userId = dto.getUser_id();
                if (userId == null || userId.trim().isEmpty()) {
                    log.warn("LLMUsage entry has null or empty user_id, skipping.");
                    continue;
                }
                LLMUsage existing = llmUsageRepository.findByUserId(userId);
                if (existing == null) {
                    String msg = "User not found for LLMUsage with user_id: " + userId;
                    log.error(msg);
                    throw new RuntimeException(msg);
                }
                if (dto.getCall_count() != null) {
                    int currentCount = Optional.ofNullable(existing.getCallCount()).orElse(0);
                    existing.setCallCount(currentCount + 1);
                }
                if (dto.getWeek_start() != null) existing.setWeekStart(LocalDateTime.parse(dto.getWeek_start()));
                if (dto.getWeek_end() != null) existing.setWeekEnd(LocalDateTime.parse(dto.getWeek_end()));
                if (dto.getIs_premium() != null) existing.setIsPremium(dto.getIs_premium());
                if (dto.getUser_type() != null) existing.setUserType(dto.getUser_type());
                if (dto.getCreated_at() != null) existing.setCreatedAt(LocalDateTime.parse(dto.getCreated_at()));
                if (dto.getUpdated_at() != null) existing.setUpdatedAt(LocalDateTime.parse(dto.getUpdated_at()));
                llmUsageRepository.save(existing);
                llmUpdatedCount.incrementAndGet();
                log.info("Updated LLM usage for user_id: {}", userId);
            }
        }
        List<UserPremiumStatusDTO> statusList = wrapperDTO.getUser_premium_status();
        if (statusList != null) {
            for (UserPremiumStatusDTO dto : statusList) {
                String userId = dto.getUser_id();
                if (userId == null || userId.trim().isEmpty()) {
                    log.warn("UserPremiumStatus entry has null or empty user_id, skipping.");
                    continue;
                }
                Optional<UserPremiumStatus> optionalExisting = userPremiumStatusRepository.findByUserId(userId);
                if (!optionalExisting.isPresent()) {
                    String msg = "User not found for UserPremiumStatus with user_id: " + userId;
                    log.error(msg);
                    throw new RuntimeException(msg);
                }
                UserPremiumStatus existing = optionalExisting.get();
                if (dto.getIs_premium() != null) existing.setIsPremium(dto.getIs_premium());
                if (dto.getUser_type() != null) existing.setUserType(dto.getUser_type());
                if (dto.getSubscription_type() != null) existing.setSubscriptionType(dto.getSubscription_type());
                if (dto.getPremium_start_date() != null) existing.setPremiumStartDate(LocalDateTime.parse(dto.getPremium_start_date()));
                if (dto.getPremium_end_date() != null) existing.setPremiumEndDate(LocalDateTime.parse(dto.getPremium_end_date()));
                if (dto.getCreated_at() != null) existing.setCreatedAt(LocalDateTime.parse(dto.getCreated_at()));
                if (dto.getUpdated_at() != null) existing.setUpdatedAt(LocalDateTime.parse(dto.getUpdated_at()));
                userPremiumStatusRepository.save(existing);
                premiumUpdatedCount.incrementAndGet();
                log.info("Updated premium status for user_id: {}", userId);
            }
        }
        String message = String.format("Successfully updated %d LLM usage entries and %d premium status entries.", llmUpdatedCount.get(), premiumUpdatedCount.get());
        log.info(message);
        return message;
    }

    @Override
    public CommonV2Response getSystemConfig() {
        CommonV2Response response = new CommonV2Response();
        response.setId("system_config");
        response.setCreatedTime(LocalDateTime.now().toString());
        try {
            List<Map<String, Object>> configList = systemConfigRepository.findAll()
                    .stream()
                    .map(config -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("config_key", config.getConfigKey());
                        map.put("config_value", config.getConfigValue());
                        map.put("data_type", config.getDataType());
                        map.put("description", config.getDescription());
                        map.put("category", config.getCategory());
                        map.put("created_at", config.getCreatedAt());
                        map.put("updated_at", config.getUpdatedAt());
                        return map;
                    }).collect(Collectors.toList());
            response.putField("success", true);
            response.putField("message", "System configuration fetched successfully.");
            response.putField("data", configList);
        } catch (Exception e) {
            log.error("Error while fetching system config: {}", e.getMessage());
            response.putField("success", false);
            response.putField("message", "Failed to fetch system configuration.");
            response.putField("data", Collections.emptyList());
        }
        return response;
    }

    @Override
    public Map<String, List<CommonV2Response>> updateProfile(CreateProfileRequest request) {
        List<Users> usersToUpdate = new ArrayList<>();

        for (CommonV2Request record : request.getRecords()) {
            Map<String, String> fields = record.getFields();
            String email = fields.get("Email");
            EqualEdEnums.UserRole role = Optional.ofNullable(fields.get("role"))
                    .map(String::toUpperCase).map(EqualEdEnums.UserRole::valueOf)
                    .orElseThrow(() -> new IllegalArgumentException("Role is required for user with email: " + email));
            Users user = userRepository.findByEmailAndRole(email, role)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + email + " with role: " + role));
            Optional.ofNullable(fields.get("Username")).ifPresent(user::setUsername);
            Optional.ofNullable(fields.get("Firstname")).ifPresent(user::setFirstname);
            Optional.ofNullable(fields.get("Lastname")).ifPresent(user::setLastname);
            Optional.ofNullable(fields.get("Countrycode")).ifPresent(user::setCountryCode);
            Optional.ofNullable(fields.get("Statecode")).ifPresent(user::setStateCode);
            Optional.ofNullable(fields.get("Schoolname")).ifPresent(user::setSchoolName);
            Optional.ofNullable(fields.get("Dob")).ifPresent(d -> user.setDob(LocalDate.parse(d)));
            Optional.ofNullable(fields.get("year_group_id"))
                    .filter(StringUtils::isNumeric).map(Integer::parseInt).map(this::getYearGroup).ifPresent(user::setYearGroup);
            Optional.ofNullable(fields.get("role"))
                    .map(String::toUpperCase).map(EqualEdEnums.UserRole::valueOf).ifPresent(user::setRole);
            user.setLastUpdatedOn(Instant.now());
            usersToUpdate.add(user);
        }
        List<Users> updatedUsers = userRepository.saveAll(usersToUpdate);
        List<CommonV2Response> responses = updatedUsers.stream()
                .map(EqualEdServiceImplV2::createCommonUserResponse).collect(Collectors.toList());
        return generateResponse(responses);
    }

    @Override
    public Map<String, List<CommonV2Response>> updateProfile(CreateProfileRequest request, Integer guardianId) {
        Users guardian = userRepository.findById(guardianId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Guardian Id"));
        List<Users> usersToUpdate = new ArrayList<>();
        for (CommonV2Request record : request.getRecords()) {
            Map<String, String> fields = record.getFields();
            Optional.ofNullable(fields.get("Username")).ifPresent(guardian::setUsername);
            Optional.ofNullable(fields.get("Email")).ifPresent(guardian::setEmail);
            Optional.ofNullable(fields.get("Firstname")).ifPresent(guardian::setFirstname);
            Optional.ofNullable(fields.get("Lastname")).ifPresent(guardian::setLastname);
            Optional.ofNullable(fields.get("year_group_id")).filter(StringUtils::isNumeric).map(Integer::parseInt).map(this::getYearGroup).ifPresent(guardian::setYearGroup);
            Optional.ofNullable(fields.get("role")).map(String::toUpperCase).map(EqualEdEnums.UserRole::valueOf).ifPresent(guardian::setRole);
            guardian.setLastUpdatedOn(Instant.now());
            usersToUpdate.add(guardian);
        }
        List<Users> updatedUsers = userRepository.saveAll(usersToUpdate);
        guardian.getStudents().addAll(updatedUsers);
        userRepository.save(guardian);
        List<CommonV2Response> responses = updatedUsers.stream()
                .map(EqualEdServiceImplV2::createCommonUserResponse).collect(Collectors.toList());
        return generateResponse(responses);
    }

    @Override
    public Map<String, Long> getUserRoleCounts() {
        // Initialize map with all roles
        Map<String, Long> roleCounts = new HashMap<>();
        for (EqualEdEnums.UserRole role : EqualEdEnums.UserRole.values()) {
            roleCounts.put(role.name(), 0L);
        }
        // fetch role counts from DB
        List<Object[]> results = userRepository.countUsersByRole();
        for (Object[] result : results) {
            EqualEdEnums.UserRole role = (EqualEdEnums.UserRole) result[0];
            Long count = (Long) result[1];
            roleCounts.put(role.name(), count);
        }
        return roleCounts;
    }
}

