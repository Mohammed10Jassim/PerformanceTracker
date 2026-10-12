package com.pt.PerformanceTracker.common.enums;

import com.pt.PerformanceTracker.organization.projects.enums.ProjectStatus;
import com.pt.PerformanceTracker.performance.kpaTask.entity.TaskStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/enums"})
public class EnumController {
   @GetMapping({"/project-statuses"})
   public ProjectStatus[] getProjectStatuses() {
      return ProjectStatus.values();
   }

   @GetMapping({"/task-statuses"})
   public TaskStatus[] getTaskStatuses() {
      return TaskStatus.values();
   }
}
