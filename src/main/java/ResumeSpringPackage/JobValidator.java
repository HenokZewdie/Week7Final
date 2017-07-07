package ResumeSpringPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class JobValidator implements Validator {
    @Autowired
    JobRepository jobRepository;
    public boolean supports(Class<?> clazz){
        return Job.class.isAssignableFrom(clazz);
    }
    public void validate(Object target, Errors errors){
        Job job = (Job) target;
        String title = job.getTitle();
        String employer = job.getEmployer();
        double salary = job.getSalary();
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "user.firstName.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employer", "user.lastName.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salary", "user.username.empty");

        if(salary < 2000){
            errors.rejectValue("title","user.username.what");
        }

    }
}