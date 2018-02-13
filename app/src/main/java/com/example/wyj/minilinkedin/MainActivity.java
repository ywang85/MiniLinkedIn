package com.example.wyj.minilinkedin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wyj.minilinkedin.Utils.DateUtils;
import com.example.wyj.minilinkedin.Utils.ImageUtils;
import com.example.wyj.minilinkedin.Utils.ModelUtils;
import com.example.wyj.minilinkedin.model.BasicInfo;
import com.example.wyj.minilinkedin.model.Education;
import com.example.wyj.minilinkedin.model.Experience;
import com.example.wyj.minilinkedin.model.Project;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String MODEL_BASIC_INFO = "basic_info";
    private static final String MODEL_EDUCATIONS = "education";
    private static final String MODEL_EXPERIENCE = "experience";
    private static final String MODEL_PROJECT = "project";
    private BasicInfo basicInfo;
    private List<Education> educations;
    private List<Experience> experiences;
    private List<Project> projects;
    private static final int REQ_CODE_EDIT_EDUCATION = 100;
    private static final int REQ_CODE_EDIT_EXPERIENCE = 101;
    private static final int REQ_CODE_EDIT_PROJECT = 102;
    private static final int REQ_CODE_EDIT_BASICINFO = 103;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
//        basicInfo = new BasicInfo();
//        educations = new ArrayList<>();
//        experiences = new ArrayList<>();
//        projects = new ArrayList<>();
        setupUI();
    }

    private void loadData() {
        BasicInfo savedBasicInfo = ModelUtils.read(this,
                MODEL_BASIC_INFO,
                new TypeToken<BasicInfo>(){});
        basicInfo = savedBasicInfo == null ? new BasicInfo() : savedBasicInfo;

        List<Education> savedEducation = ModelUtils.read(this,
                MODEL_EDUCATIONS,
                new TypeToken<List<Education>>(){});
        educations = savedEducation == null ? new ArrayList<Education>() : savedEducation;

        List<Experience> savedExperience = ModelUtils.read(this,
                MODEL_EXPERIENCE,
                new TypeToken<List<Experience>>(){});
        experiences = savedExperience == null ? new ArrayList<Experience>() : savedExperience;

        List<Project> savedProjects = ModelUtils.read(this,
                MODEL_PROJECT,
                new TypeToken<List<Project>>(){});
        projects = savedProjects == null ? new ArrayList<Project>() : savedProjects;
    }


    private void setupUI() {
        setContentView(R.layout.activity_main);

        ImageButton addEducationBtn = (ImageButton) findViewById(R.id.add_education);
        addEducationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDIT_EDUCATION);
            }
        });

        ImageButton addExperienceBtn = (ImageButton) findViewById(R.id.add_experience);
        addExperienceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDIT_EXPERIENCE);
            }
        });

        ImageButton addProjectBtn = (ImageButton) findViewById(R.id.add_projects);
        addProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDIT_PROJECT);
            }
        });

        setupBasicInfo();
        setupEducations();
        setupExperiences();
        setupProjects();
    }

    private void setupBasicInfo() {
        TextView fullName = findViewById(R.id.full_name);
        if (TextUtils.isEmpty(basicInfo.name)) {
            fullName.setText("Your name");
        } else {
            fullName.setText(basicInfo.name);
        }
        TextView email = findViewById(R.id.email);
        if (TextUtils.isEmpty(basicInfo.email)) {
            email.setText("Your email");
        } else {
            email.setText(basicInfo.email);
        }
        ImageView userPicture = findViewById(R.id.user_picture);
        if (basicInfo.imageUri != null) {
            ImageUtils.loadImage(this, basicInfo.imageUri, userPicture);
        } else {
            userPicture.setImageResource(R.drawable.ic_account_box_black_24dp);
        }
        findViewById(R.id.edit_user_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BasicInfoEditActivity.class);
                intent.putExtra(BasicInfoEditActivity.KEY_BASIC_INFO, basicInfo);
                startActivityForResult(intent, REQ_CODE_EDIT_BASICINFO);
            }
        });
    }


    private void setupEducations() {
        LinearLayout educationsLayout = findViewById(R.id.education_list);
        educationsLayout.removeAllViews();
        // 从list中读取学历信息，加载默认布局，写入学历信息，加入到UI
        for (Education e : educations) {
            View educationView = getLayoutInflater().inflate(R.layout.education_item, null);
            setupEducation(educationView, e);
            educationsLayout.addView(educationView);
        }
    }

    private void setupEducation(View educationView, final Education e) {
        String dateString = DateUtils.dateToString(e.startDate) + " ~ " + DateUtils.dateToString(e.endDate);
        TextView school = educationView.findViewById(R.id.education_school);
        school.setText(e.school + " " + e.major + "(" + dateString + ")");
        TextView courses = educationView.findViewById(R.id.education_courses);
        courses.setText(formatItems(e.course));

        educationView.findViewById(R.id.edit_education).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                intent.putExtra(EducationEditActivity.KEY_EDUCATION, e);
                startActivityForResult(intent, REQ_CODE_EDIT_EDUCATION);
            }
        });
    }

    private void setupExperiences() {
        LinearLayout experiencesLayout = findViewById(R.id.experience_list);
        experiencesLayout.removeAllViews();
        for (Experience e : experiences) {
            View experienceView = getLayoutInflater().inflate(R.layout.experience_item, null);
            setupExperience(experienceView, e);
            experiencesLayout.addView(experienceView);
        }
    }


    private void setupExperience(View experienceView, final Experience e) {
        String dateString = DateUtils.dateToString(e.startDate) + " ~ " + DateUtils.dateToString(e.endDate);
        TextView company = experienceView.findViewById(R.id.company);
        company.setText(e.company + " (" + dateString + ")");
        TextView description = experienceView.findViewById(R.id.work_description);
        description.setText(formatItems(e.details));
        experienceView.findViewById(R.id.edit_experience).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                intent.putExtra(ExperienceEditActivity.KEY_EXPERIENCE, e);
                startActivityForResult(intent, REQ_CODE_EDIT_EXPERIENCE);
            }
        });
    }

    private void setupProjects() {
        LinearLayout projectListLayout = (LinearLayout) findViewById(R.id.projects_list);
        projectListLayout.removeAllViews();
        for (Project project : projects) {
            View projectView = getLayoutInflater().inflate(R.layout.project_item, null);
            setupProject(projectView, project);
            projectListLayout.addView(projectView);
        }
    }

    private void setupProject(@NonNull View projectView, final Project project) {
        String dateString = DateUtils.dateToString(project.startDate)
                + " ~ " + DateUtils.dateToString(project.endDate);
        ((TextView) projectView.findViewById(R.id.project_name))
                .setText(project.name + " (" + dateString + ")");
        ((TextView) projectView.findViewById(R.id.project_description))
                .setText(formatItems(project.details));
        projectView.findViewById(R.id.edit_project).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                intent.putExtra(ProjectEditActivity.KEY_PROJECT, project);
                startActivityForResult(intent, REQ_CODE_EDIT_PROJECT);
            }
        });
    }


    private String formatItems(List<String> course) {
        StringBuilder sb = new StringBuilder();
        for (String s : course) {
            sb.append(" - ").append(s).append("\n");
        }
        if (sb.length() > 0) { // 删除最后的回车
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_EDIT_BASICINFO:
                    BasicInfo basicInfo = data.getParcelableExtra(BasicInfoEditActivity.KEY_BASIC_INFO);
                    updateBasicInfo(basicInfo);
                    break;
                case REQ_CODE_EDIT_EDUCATION:
                    String educationId = data.getStringExtra(EducationEditActivity.KEY_EDUCATION_ID);
                    if (educationId != null) {
                        deleteEducation(educationId);
                    } else {
                        Education education = data.getParcelableExtra(EducationEditActivity.KEY_EDUCATION);
                        updateEducation(education);
                    }
                    break;
                case REQ_CODE_EDIT_EXPERIENCE:
                    String experienceId = data.getStringExtra(ExperienceEditActivity.KEY_EXPERIENCE_ID);
                    if (experienceId != null) {
                        deleteExperience(experienceId);
                    } else {
                        Experience experience = data.getParcelableExtra(ExperienceEditActivity.KEY_EXPERIENCE);
                        updateExperience(experience);
                    }
                    break;
                case REQ_CODE_EDIT_PROJECT:
                    String projectId = data.getStringExtra(ProjectEditActivity.KEY_PROJECT_ID);
                    if (projectId != null) {
                        deleteProject(projectId);
                    } else {
                        Project project = data.getParcelableExtra(ProjectEditActivity.KEY_PROJECT);
                        updateProject(project);
                    }
                    break;
            }
        }

    }


    private void updateBasicInfo(BasicInfo basicInfo) {
        ModelUtils.save(this, MODEL_BASIC_INFO, basicInfo);
        this.basicInfo = basicInfo;
        setupBasicInfo();
    }

    private void deleteEducation(String educationId) {
        for (int i = 0; i < educations.size(); i++) {
            if (educations.get(i).id.equals(educationId)) {
                educations.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setupEducations();
    }

    private void updateEducation(Education education) {
        boolean found = false;
        for (int i = 0; i < educations.size(); i++) {
            Education e = educations.get(i);
            if (e.id.equals(education.id)) {
                found = true;
                educations.set(i, education);
                break;
            }
        }
        if (!found) {
            educations.add(education);
        }
        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setupEducations();
    }


    private void deleteExperience(String experienceId) {
        for (int i = 0; i < experiences.size(); i++) {
            if (experiences.get(i).id.equals(experienceId)) {
                experiences.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_EXPERIENCE, experiences);
        setupExperiences();
    }

    private void updateExperience(Experience experience) {
        boolean found = false;
        Log.d("myid", experiences.size() + "");
        for (int i = 0; i < experiences.size(); i++) {
            Experience e = experiences.get(i);
            Log.d("myid", e.id);
            if (e.id.equals(experience.id)) {
                found = true;
                experiences.set(i, experience);
                break;
            }
        }
        if (!found) {
            experiences.add(experience);
        }
        ModelUtils.save(this, MODEL_EXPERIENCE, experiences);
        setupExperiences();
    }

    private void updateProject(Project project) {
        boolean found = false;
        Log.d("myid", projects.size() + "");
        for (int i = 0; i < projects.size(); i++) {
            Project p = projects.get(i);
            Log.d("myid", p.id);
            if (p.id.equals(project.id)) {
                found = true;
                projects.set(i, project);
                break;
            }
        }
        if (!found) {
            projects.add(project);
        }
        ModelUtils.save(this, MODEL_PROJECT, projects);
        setupProjects();
    }

    private void deleteProject(String projectId) {
        for (int i = 0; i < projects.size(); i++) {
            Project p = projects.get(i);
            if (p.id.equals(projectId)) {
                projects.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_PROJECT, projects);
        setupProjects();
    }


}
