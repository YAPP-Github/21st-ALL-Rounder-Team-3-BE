package yapp.allround3.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import yapp.allround3.project.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
