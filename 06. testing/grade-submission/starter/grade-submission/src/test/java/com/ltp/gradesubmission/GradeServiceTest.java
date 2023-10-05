package com.ltp.gradesubmission;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ltp.gradesubmission.pojo.Grade;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.service.GradeService;

@ExtendWith(MockitoExtension.class)
class GradeServiceTest {
  
  @Mock
  private GradeRepository gradeRepository;

  @InjectMocks
  private GradeService gradeService;

    @Test
    void getGradesFromRepoTest() {
    when(gradeRepository.getGrades()).thenReturn(Arrays.asList(
      new Grade("Harry", "Potions", "C-"),
      new Grade("Hermione", "Arithmancy", "A+")
    ));
    List<Grade> result = gradeService.getGrades();

    assertEquals("Harry", result.get(0).getName());
    assertEquals("Arithmancy", result.get(1).getSubject());
  }

    @Test
    void gradeIndexTest() {
    Grade grade = new Grade("Harry", "Potions", "C-");
    when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
    when(gradeRepository.getGrade(0)).thenReturn(grade);   

    int valid = gradeService.getGradeIndex(grade.getId());
    int notFound = gradeService.getGradeIndex("1234");

    assertEquals(0, valid);
    assertEquals(Constants.NOT_FOUND, notFound);
  }

    @Test
    void returnGradeByIdTest() {
    Grade grade = new Grade("Harry", "Potions", "C-");
    when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
    when(gradeRepository.getGrade(0)).thenReturn(grade);

    String id = grade.getId();
    Grade result = gradeService.getGradeById(id);

    assertEquals(grade, result);
  }

    @Test
    void addGradeTest() {
    Grade grade = new Grade("Harry", "Potions", "C-");
    when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
    when(gradeRepository.getGrade(0)).thenReturn(grade);

    Grade newGrade = new Grade("Hermione", "Arithmancy", "A+");
    gradeService.submitGrade(newGrade);
    verify(gradeRepository, times(1)).addGrade(newGrade);
  }

    @Test
    void updateGradeTest() {
    Grade grade = new Grade("Harry", "Potions", "C-");
    when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
    when(gradeRepository.getGrade(0)).thenReturn(grade);

    grade.setScore("A+");
    gradeService.submitGrade(grade);
    verify(gradeRepository, times(1)).updateGrade(grade, 0);
    
  }
}