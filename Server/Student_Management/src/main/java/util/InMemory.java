package util;

import dto.StudentDTO;
import java.util.ArrayList;
import java.util.List;

public class InMemory {
    static List<StudentDTO> studentList = new ArrayList<>();

    public static boolean saveStudent(StudentDTO dto){
        if (isExist(dto)){
            return false;
        }
        return studentList.add(dto);
    }

    public static boolean isExist(StudentDTO dto){
        return studentList.contains(dto);
    }

    public static boolean updateStudent(StudentDTO dto){
        if (!isExist(dto)){
            return false;
        }
        for (StudentDTO studentDTO : studentList) {
            if (studentDTO.getId().equals(dto.getId())){
                studentDTO.setName(dto.getName());
                studentDTO.setAge(dto.getAge());
                studentDTO.setAddress(dto.getAddress());
                return true;
            }
        }
        return false;
    }

    public static boolean deleteStudent(StudentDTO dto){
        return studentList.remove(dto);
    }

    public static List<StudentDTO> getAllStudent(){
        return studentList;
    }

    public static StudentDTO getStudentById(String id){
        for (StudentDTO studentDTO : studentList) {
            if (studentDTO.getId().equals(id)){
                return studentDTO;
            }
        }
        return null;
    }
}
