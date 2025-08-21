package com.schoolmgt.auth.utils;

public class IdGenerators {

    private IdGenerators() {
    }

    // School ID: SCH-0001
    public static String generateSchoolId(Long id) {
        return String.format("SCH-%04d", id);
    }

    // Department ID: DEP-0001-0001  (department seq - school seq, stripped)
    public static String generateDepartmentId(Long departmentId, String schoolId) {
        String schoolSeq = stripPrefix(schoolId, "SCH-");
        return String.format("DEP-%04d-%s", departmentId, schoolSeq);
    }

    // Semester ID: SEM-0001-0001  (semester seq - school seq, stripped)
    public static String generateSemesterId(Long semesterId, String schoolId) {
        String schoolSeq = stripPrefix(schoolId, "SCH-");
        return String.format("SEM-%04d-%s", semesterId, schoolSeq);
    }

    // Class ID: CLS-0001-0001  (class seq - school seq, stripped)
    public static String generateClassId(Long classId, String schoolId) {
        String schoolSeq = stripPrefix(schoolId, "SCH-");
        return String.format("CLS-%04d-%s", classId, schoolSeq);
    }

    // Student ID: STU-00001-0001  (student seq - class seq, stripped)
    public static String generateStudentId(Long studentId, String classId) {
        String classSeq = stripPrefix(classId, "CLS-");
        return String.format("STU-%05d-%s", studentId, classSeq);
    }

    // Teacher ID: TEA-00001-0001  (teacher seq - school seq, stripped)
    public static String generateTeacherId(Long teacherId, String schoolId) {
        String schoolSeq = stripPrefix(schoolId, "SCH-");
        return String.format("TEA-%05d-%s", teacherId, schoolSeq);
    }

    // Subject ID: SUB-00001-0001  (subject seq - school seq, stripped)
    public static String generateSubjectId(Long subjectId, String schoolId) {
        String schoolSeq = stripPrefix(schoolId, "SCH-");
        return String.format("SUB-%05d-%s", subjectId, schoolSeq);
    }

    // Helper to remove a prefix safely
    private static String stripPrefix(String id, String prefix) {
        if (id != null && id.startsWith(prefix)) {
            return id.substring(prefix.length());
        }
        return id;
    }
}
