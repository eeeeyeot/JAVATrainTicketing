SELECT *
FROM STUDENT
WHERE YEAR = 3;

SELECT count(*)
FROM STUDENT
WHERE YEAR = 3;

SELECT COUNT(dept_id)
FROM STUDENT;

SELECT dept_id
FROM STUDENT;

SELECT *
FROM EMP

SELECT COUNT(COMM)
FROM EMP

SELECT Count(Sal)
FROM emp

SELECT count(dept_id)
FROM student

SELECT count(DISTINCT dept_id)
FROM student

SELECT *
FROM STUDENT

SELECT count(*)
FROM STUDENT s , DEPARTMENT d 
WHERE s.DEPT_ID  = d.DEPT_ID AND d.DEPT_NAME = '��ǻ�Ͱ��а�'

SELECT count(*)
FROM STUDENT s , DEPARTMENT d 
WHERE s.DEPT_ID  = d.DEPT_ID AND d.DEPT_NAME = '��ǻ�Ͱ��а�'
AND YEAR  = 3;

SELECT *
FROM STUDENT s , DEPARTMENT d 
WHERE s.DEPT_ID  = d.DEPT_ID AND d.DEPT_NAME = '��ǻ�Ͱ��а�'
AND YEAR = 3

SELECT COUNT(*)
FROM PROFESSOR

SELECT SUM(2020 - year_emp)
FROM PROFESSOR

SELECT * FROM DEPT

SELECT sum (sal)
FROM emp;

SELECT sum(sal)
FROM emp 
WHERE job = 'ANALYST'

SELECT sum (sal)
FROM emp e, DEPT d 
WHERE e.DEPTNO = d.DEPTNO AND dname = 'RESEARCH'

SELECT min(sal) �ּ�, max(sal) �ִ�, avg(sal) ���
FROM EMP e, DEPT d 
WHERE e.DEPTNO = d.DEPTNO AND dname = 'ACCOUNTING'

SELECT *
FROM EMP e, DEPT d 
WHERE e.DEPTNO = d.DEPTNO AND dname = 'ACCOUNTING'

SELECT * FROM EMP e 

SELECT dept_id, count(*)
FROM STUDENT
GROUP BY DEPT_ID
--ORDER BY DEPT_ID

SELECT dept_name, count(*)
FROM STUDENT s , DEPARTMENT d 
WHERE s.DEPT_ID = d.DEPT_ID 
GROUP BY DEPT_NAME

SELECT *
FROM STUDENT s , DEPARTMENT d 
WHERE s.DEPT_ID = d.DEPT_ID 

SELECT dname, count(*), avg(sal), max(sal), min(sal)
FROM emp e, DEPT d 
WHERE e.DEPTNO  = d.DEPTNO 
GROUP BY dname

SELECT dept_name �а�, count(*) �ο��� , avg(2020 - YEAR_EMP) �����������, max(2020 - year_emp) �ִ���������
FROM PROFESSOR p, DEPARTMENT d 
WHERE p.DEPT_ID = d.DEPT_ID 
GROUP BY DEPT_NAME

SELECT dept_name �а�, count(*) �ο���, avg(2012 - year_emp) �����������, max(2012 - year_emp) �ִ���������
FROM PROFESSOR p, DEPARTMENT d 
WHERE p.DEPT_ID = d.DEPT_ID 
GROUP BY DEPT_NAME 
HAVING avg(2012 - YEAR_EMP) >= 10

SELECT dname, count(*), avg(sal), max(sal), min(sal)
FROM EMP e, DEPT d 
WHERE e.DEPTNO = d.DEPTNO 
GROUP BY DNAME 
HAVING count(*) >= 5

SELECT *
FROM EMP
WHERE COMM IS NOT NULL

SELECT title
FROM COURSE
WHERE COURSE_ID  IN (
	SELECT DISTINCT COURSE_ID
	FROM CLASS
	WHERE CLASSROOM = '301ȣ'
)

SELECT DISTINCT title
FROM COURSE c1, class c2
WHERE c1.COURSE_ID = c2.COURSE_ID 
AND classroom = '301ȣ'

SELECT title
FROM COURSE 
WHERE COURSE_ID NOT IN (
	SELECT DISTINCT COURSE_ID 
	FROM CLASS
	WHERE YEAR = 2012 AND SEMESTER = 2
)

SELECT DISTINCT title
FROM COURSE
MINUS
SELECT DISTINCT c2.TITLE
FROM CLASS c1, COURSE c2
WHERE c1.COURSE_ID = c2.COURSE_ID and
c1."YEAR" = 2012 AND c1.SEMESTER = 2

SELECT * FROM TAKES

CREATE VIEW v_takes AS 
	SELECT stu_id, class_id
	FROM takes
	
SELECT * FROM v_takes

CREATE OR REPLACE VIEW cs_student AS
	SELECT s.STU_ID , s.RESIDENT_ID , s.NAME , s.YEAR, s.ADDRESS , s.DEPT_ID
	FROM student s, department d
	WHERE s.DEPT_ID = d.DEPT_ID AND 
		d.DEPT_NAME = '��ǻ�Ͱ��а�'

SELECT * FROM cs_student

INSERT INTO V_TAKES 
VALUES ('1292502', 'C101-01')

SELECT * FROM V_TAKES

SELECT * FROM TAKES

SELECT * FROM Tab


SELECT deptno AS �μ��ڵ�, dname AS �μ���, loc AS ����
FROM DEPT;

SELECT empno, ename || ' (' || job || ')' employee
FROM emp;

SELECT ename, round(sal/12, 1), TRUNC(sal/12, 1)
FROM emp

SELECT SYSDATE, EXTRACT(MONTH FROM sysdate), 
EXTRACT(DAY FROM sysdate)
FROM DUAL 

SELECT sysdate, TO_NUMBER(TO_CHAR(SYSDATE,'YYYY')) year,
TO_NUMBER(TO_CHAR(SYSDATE, 'MM')) month,
TO_NUMBER(TO_CHAR(SYSDATE, 'DD')) day
FROM dual;

SELECT ename, hiredate, TO_NUMBER(to_char(hiredate, 'YYYY')) �Ի�⵵,
TO_NUMBER(to_char(hiredate, 'MM')) �Ի��,
TO_NUMBER(to_char(hiredate, 'DD')) �Ի���
FROM EMP

SELECT to_char(sysdate, 'YYYY/MM/DD') ��¥,
TO_CHAR(SYSDATE, 'YYYY. MON. DAY') ������
FROM DUAL 

SELECT ename,
CASE WHEN sal > 2000
THEN SAL 
ELSE 2000
END revised_salary
FROM EMP

SELECT loc,
CASE LOC 
WHEN 'NEW YORK' THEN 'EAST'
WHEN 'BOSTON' THEN 'EAST'
WHEN 'CHICAGO' THEN 'CENTER'
WHEN 'DALLAS' THEN 'CENTER'
ELSE 'ETC'
END AS AREA
FROM DEPT

SELECT loc,
DECODE(loc,
'NEW YORK', 'EAST',
'BOSTON', 'EAST',
'DALLAS', 'CENTER',
'CHICAGO', 'CENTER') area
FROM DEPT

SELECT ename,
CASE 
WHEN sal >= 3000 THEN 'high'
WHEN sal >= 1000 THEN 'mid'
ELSE 'low'
END AS SALARY_G
FROM emp

SELECT ename, sal, NVL(comm, 0) comm, sal + NVL(comm, 0)
FROM emp

SELECT empno, ename, sal,
CASE 
WHEN comm IS NULL THEN 0
ELSE COMM 
END AS commission
FROM EMP

SELECT * FROM EMP
WHERE comm IS NULL

SELECT * FROM EMP
WHERE comm IS NOT NULL 

SELECT * FROM(
	SELECT * FROM EMP
	ORDER BY SAL DESC 
)
WHERE rownum <= 5

SELECT ename, sal, comm, sal + NVL(comm, 0) salSum FROM EMP
ORDER BY 4 DESC

SELECT * FROM (
	SELECT ename, sal, comm, sal + NVL(comm, 0) salSum FROM EMP
	ORDER BY 4 DESC
)
WHERE rownum <= 5

SELECT * FROM emp
WHERE sal >= 1500 AND sal <= 2500

SELECT * FROM EMP
WHERE sal BETWEEN 1500 AND 2500

SELECT dname, job,
count(*) "Total Empl",
SUM(sal) "Total sal"
FROM emp, DEPT
WHERE dept.DEPTNO = emp.DEPTNO 
GROUP BY dname, job
ORDER BY dname, job

SELECT dname, job,
count(*) "Total Empl",
SUM(sal) "Total sal"
FROM emp, DEPT
WHERE dept.DEPTNO = emp.DEPTNO 
GROUP BY ROLLUP (dname, JOB)
ORDER BY dname, job

SELECT * FROM DEPT

SELECT deptno FROM (
SELECT deptno FROM DEPT
ORDER BY DEPTNO desc)
WHERE rownum <= 1

DELETE FROM dept 
WHERE DEPTNO >= 50

SELECT * FROM emp

SELECT ename, count(*)
FROM EMP
GROUP BY ENAME





































