package test;

import model.Member;
import model.StudentMember;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MemberTest {

    @Test
    void calculateFee_forNormalMember() {
        Member m = new Member("M1", "Normal User", "normal@mail.com");

        double fee = m.calculateFee(4);  // 4 gün geç iade

        assertEquals(8.0, fee);   // normal: dailyFee = 2 → 4*2 = 8
    }

    @Test
    void calculateFee_forStudentMember_discountApplied() {
        StudentMember sm = new StudentMember("S1", "Student", "s@mail.com", "CS");

        double fee = sm.calculateFee(4); // öğrenci 50% indirimli

        assertEquals(4.0, fee);  // öğrenci: 4 gün → 4*2*0.5 = 4
    }

    @Test
    void studentMemberMatches_worksCorrectly() {
        StudentMember sm = new StudentMember("S1", "Student", "s@mail.com", "CS");

        assertTrue(sm.matches("stu"));
        assertTrue(sm.matches("cs"));
        assertFalse(sm.matches("xyz"));
    }
}

