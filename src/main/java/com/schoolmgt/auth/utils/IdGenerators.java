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
    public static String generateStudentId(Long studentId, String schoolId) {
        String classSeq = stripPrefix(schoolId, "SCH-");
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

    // Fee Type ID: FEE-TYPE-0001-0001  (fee type seq - school seq, stripped)
    public static String generateFeeTypeId(Long feeTypeId, String schoolId) {
        String schoolSeq = stripPrefix(schoolId, "SCH-");
        return String.format("FEE-TYPE-%04d-%s", feeTypeId, schoolSeq);
    }

    // Fee Structure ID: FEE-STR-0001-0001-V1  (fee structure seq - class seq, stripped - version)
    public static String generateFeeStructureId(Long feeStructureId, String classId, Integer version) {
        String classSeq = stripPrefix(classId, "CLS-");
        return String.format("FEE-STR-%04d-%s-V%d", feeStructureId, classSeq, version);
    }

    // Assessment ID: ASSESS-0001-0001  (assessment seq - student seq, stripped)
    public static String generateAssessmentId(Long assessmentId, String studentId) {
        String studentSeq = stripPrefix(studentId, "STU-");
        return String.format("ASSESS-%04d-%s", assessmentId, studentSeq);
    }

    // Additional Charge ID: CHARGE-0001-0001  (charge seq - student seq, stripped)
    public static String generateAdditionalChargeId(Long chargeId, String studentId) {
        String studentSeq = stripPrefix(studentId, "STU-");
        return String.format("CHARGE-%04d-%s", chargeId, studentSeq);
    }

    // Payment ID: PAY-0001-0001  (payment seq - student seq, stripped)
    public static String generatePaymentId(Long paymentId, String studentId) {
        String studentSeq = stripPrefix(studentId, "STU-");
        return String.format("PAY-%04d-%s", paymentId, studentSeq);
    }

    // Scholarship ID: SCHOL-0001-0001  (scholarship seq - school seq, stripped)
    public static String generateScholarshipId(Long scholarshipId, String schoolId) {
        String schoolSeq = stripPrefix(schoolId, "SCH-");
        return String.format("SCHOL-%04d-%s", scholarshipId, schoolSeq);
    }

    // Helper to remove a prefix safely
    private static String stripPrefix(String id, String prefix) {
        if (id != null && id.startsWith(prefix)) {
            return id.substring(prefix.length());
        }
        return id;
    }
}
