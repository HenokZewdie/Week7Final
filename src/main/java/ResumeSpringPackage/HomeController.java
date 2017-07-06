package ResumeSpringPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.Iterator;

@Controller
public class HomeController {

    @Autowired
    private UserValidator userValidator;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private SkillRepository skillRepository;

    String emailSession;
    @RequestMapping("/")
    public String index(){
        return "home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
    public String getLogin(Model model, User user, Principal principal){
        user.setEmail(principal.getName());
        String emailSession = user.getEmail();
        user = userRepository.findByUsername(emailSession);
        String typeRole = user.getUserType();
        if(typeRole.equalsIgnoreCase("seeker")){return "redirect:/jobseeker";}
        else return "recruiterlogin";
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        model.addAttribute("user", user);
        emailSession = user.getEmail();
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "registration";
        } else {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Successfully Created");
        }
        user = userRepository.findByUsername(user.getUsername());
        model.addAttribute("register1", user);
        return "displaySearch";
    }

    /*Education controller*/
    @RequestMapping(value = "/education", method = RequestMethod.GET)
    public String EducationGet(Model model){
        model.addAttribute("education", new Education());
        return "education";
    }
    @RequestMapping(value = "/education", method = RequestMethod.POST)
    public String EducationPost(@ModelAttribute Education education, Model model){
        education.setEmail(emailSession);
        educationRepository.save(education);
        model.addAttribute("education", new Education());
        return "education";
    }
    /*Experience controller*/
    @RequestMapping(value = "/experience", method = RequestMethod.GET)
    public String ExperienceGet(Model model){
        model.addAttribute("experience", new Experience());
        return "experience";
    }
    @RequestMapping(value = "/experience", method = RequestMethod.POST)
    public String ExperiencePost(@ModelAttribute Experience experience, Model model){
        experience.setEmail(emailSession);
        experienceRepository.save(experience);
        model.addAttribute("experience", new Experience());
        return "experience";
    }
    /*Skill controller*/
    @RequestMapping(value = "/skill", method = RequestMethod.GET)
    public String SkillGet(Model model){
        model.addAttribute("skill", new Skill());
        return "skill";
    }
    @RequestMapping(value = "/skill", method = RequestMethod.POST)
    public String SkillPost(@ModelAttribute Skill skill, Model model){
        skill.setEmail(emailSession);
        skillRepository.save(skill);
        model.addAttribute("skill", new Skill());
        return "skill";
    }
    @RequestMapping(value = "displayAll", method = RequestMethod.GET)
    public String DisplayAll( Model model, User user){


        user = userRepository.findByEmail(emailSession);
        Iterable<Education> Educvalues = educationRepository.findByEmail(emailSession);
        Iterable<Experience> Expvalues = experienceRepository.findByEmail(emailSession);
        Iterable<Skill> Skillvalues = skillRepository.findByEmail(emailSession);
        model.addAttribute("values", user);
        model.addAttribute("Educvalues", Educvalues);
        model.addAttribute("Expvalues", Expvalues);
        model.addAttribute("Skillvalues", Skillvalues);
        return "displayAll";
    }

    @RequestMapping(value="/vacancy", method = RequestMethod.GET)
    public String jobPostGet(Model model){
        model.addAttribute(new Job());
        return "vacancy";
    }
    @RequestMapping(value="/vacancy", method = RequestMethod.POST)
    public String potJob(@ModelAttribute Job job, Model model, Principal principal){
        job.setDate(new Date());
        job.setPostedBy(principal.getName());
        jobRepository.save(job);
        return "recruiterlogin";
    }

    @RequestMapping(value = "/jobseeker", method = RequestMethod.GET)
    public String SearchByName(Model model){
        model.addAttribute("job",new Job());
        return "jobseeker";
    }
    @RequestMapping(value = "/jobseeker", method = RequestMethod.POST)
    public String SearchPostName(@ModelAttribute Job job, Model model){
        String searchtitle = job.getTitle();
        System.out.println(searchtitle);
        Iterable<Job> iterateValue = jobRepository.findByTitle(searchtitle);
        model.addAttribute("newValue", iterateValue);
        return "display";
    }
    @RequestMapping(value = "/employerSeeker", method = RequestMethod.GET)
    public String recruiterSeeker(Model model){
        model.addAttribute("job",new Job());
        return "employerSeeker";
    }
    @RequestMapping(value = "/employerSeeker", method = RequestMethod.POST)
    public String recruiterSeekerPost(@ModelAttribute Job job, Model model){
        String searchtitle = job.getEmployer();
        System.out.println(searchtitle);
        Iterable<Job> iterateValue = jobRepository.findByEmployer(searchtitle);
        model.addAttribute("employerSeeker", iterateValue);
        /*Iterator<Job> test = iterateValue.iterator();
        while(test.hasNext()){
            System.out.println(test.next().getEmployer());
        }*/
        return "display";
    }

    public UserValidator getUserValidator() {
        return userValidator;
    }
    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }
}