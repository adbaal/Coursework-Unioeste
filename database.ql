CREATE DATABASE dbfinalproject;


CREATE USER 'unioeste'@'%' IDENTIFIED BY 'finalproject';

GRANT ALL ON *.* TO 'unioeste'@'%' WITH GRANT OPTION;


flush privileges;


USE dbfinalproject;

CREATE TABLE tbStudent (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Name varchar(100) NOT NULL,
  Email varchar(100) DEFAULT NULL,
  MobileNumber varchar(25) DEFAULT NULL,
  Gender ENUM('MALE', 'FEMALE', 'UNDEFINED') NOT NULL,
  PRIMARY KEY (Id),
  UNIQUE KEY (Name)
);


CREATE TABLE tbInstitution (
  Id int(11) NOT NULL AUTO_INCREMENT,
  AbbreviationOrAcronym varchar(30) NOT NULL,
  Name varchar(100) NOT NULL,
  PRIMARY KEY (Id),
  UNIQUE KEY (AbbreviationOrAcronym, Name)
);


CREATE TABLE tbSupervisorOrEvaluator (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Name varchar(100) NOT NULL,
  Email varchar(100) DEFAULT NULL,
  MobileNumber varchar(25) DEFAULT NULL,
  Gender ENUM('MALE', 'FEMALE', 'UNDEFINED') NOT NULL,
  InstitutionId int(11) NOT NULL,
  PRIMARY KEY (Id),
  FOREIGN KEY (InstitutionId) REFERENCES tbInstitution (id),
  UNIQUE KEY (Name)
);


CREATE TABLE tbFinalProject (
  Id int(11) NOT NULL AUTO_INCREMENT,
  StudentId int(11) NOT NULL,
  SupervisorId int(11) DEFAULT NULL,
  CoSupervisorId int(11) DEFAULT NULL,
  Title varchar(250) DEFAULT NULL,
  PRIMARY KEY (Id),
  FOREIGN KEY (StudentId) REFERENCES tbStudent (id),
  FOREIGN KEY (SupervisorId) REFERENCES tbSupervisorOrEvaluator (id),
  FOREIGN KEY (CoSupervisorId) REFERENCES tbSupervisorOrEvaluator (id),
  UNIQUE KEY (StudentId)
);


CREATE TABLE tbFinalProjectDefense (
  Id int(11) NOT NULL AUTO_INCREMENT,
  FinalProjectId int(11) NOT NULL,
  DefenseDate datetime(6) DEFAULT NULL,
  Location varchar(100) DEFAULT NULL,
  Evaluator1Id int(11) DEFAULT NULL,
  Evaluator2Id int(11) DEFAULT NULL,
  Evaluator3Id int(11) DEFAULT NULL,
  Evaluator4Id int(11) DEFAULT NULL,
  Evaluator5Id int(11) DEFAULT NULL,
  Evaluator6Id int(11) DEFAULT NULL,
  PRIMARY KEY (Id),
  FOREIGN KEY (FinalProjectId) REFERENCES tbFinalProject (id),
  FOREIGN KEY (Evaluator1Id) REFERENCES tbSupervisorOrEvaluator (id),
  FOREIGN KEY (Evaluator2Id) REFERENCES tbSupervisorOrEvaluator (id),
  FOREIGN KEY (Evaluator3Id) REFERENCES tbSupervisorOrEvaluator (id),
  FOREIGN KEY (Evaluator4Id) REFERENCES tbSupervisorOrEvaluator (id),
  FOREIGN KEY (Evaluator5Id) REFERENCES tbSupervisorOrEvaluator (id),
  FOREIGN KEY (Evaluator6Id) REFERENCES tbSupervisorOrEvaluator (id),
  UNIQUE KEY (FinalProjectId)
);

CREATE TABLE tbactualacademicyear (
  id enum('only') not null unique default 'only',
  year int(11),
  primary key (id)
);

USE dbfinalproject;


INSERT INTO tbInstitution (AbbreviationOrAcronym, Name) VALUES 
  ('Unioeste','Universidade Estadual do Oeste do Paraná'),
  ('UNILA','Universidade Federal da Integração Latino-Americana'),
  ('Itaipu','Usina Hidrelétrica Itaipu Binacional'),
  ('PTI','Parque Tecnológico Itaipu'),
  ('UTFPR Medianeira','Universidade Tecnológica Federal do Paraná - Câmpus Medianeira');

INSERT INTO tbSupervisorOrEvaluator (Name, Email, MobileNumber, Gender, InstitutionId) VALUES 
  ('Adriano Batista de Almeida','adbaal@yahoo.com.br','45 999998974','MALE',1),
  ('Daniel Motter','dnmotter@gmail.com','45 999353529','MALE',1),
  ('Rui Jovita','rui@gmail.com','45 154254541','MALE',3),
  ('Dabit Sonada','dabit@gmail.com','45 84515452','MALE',4);
  
  INSERT INTO tbStudent (Name, Email, MobileNumber, Gender) VALUES 
  ('João da Silva','joao@yahoo.com','45 999999999','MALE'),
  ('Maria da Silva','maria@yahoo.com','45 999999999','FEMALE'),
  ('Paulo da Silva','paulo@yahoo.com','45 999999999','MALE'),
  ('Jairo de Oliveira','jairo@yahoo.com','45 999999999','MALE'),
  ('Marcos da Luz','marcos@yahoo.com','45 999999999','MALE');
  
  INSERT INTO tbFinalProject (StudentId, SupervisorId, CoSupervisorId, Title) VALUES 
  (1,1,null,'Introdução às microrredes de energia elétrica e seus desafios'),
  (2,2,3,'Proteção de sistemas elétricos de energia e seus desafios'),
  (3,1,4,'Modelagem de qualquer coisa'),
  (4,2,1,'Projeto de usina fotovoltaica');
  
  INSERT INTO tbFinalProjectDefense (FinalProjectId, DefenseDate, Location, Evaluator1Id, Evaluator2Id, Evaluator3Id, Evaluator4Id, Evaluator5Id, Evaluator6Id) VALUES 
  (1,'2019-12-10 14:30:00','B10 E02 S12', 1, 2, null, null, null, null),
  (2,'2019-12-10 15:30:00','B10 E02 S12', 2, 1, null, null, null, null);


  
SELECT tbFP.*, tbSt.*, tbSup.*,  tbCoSup.*, tbInstSup.*, tbInstCoSup.*
FROM tbFinalProject AS tbFP
JOIN tbStudent AS tbSt
ON tbFP.StudentId = tbSt.Id
LEFT JOIN tbSupervisorOrEvaluator AS tbSup
ON tbSup.Id = tbFP.SupervisorID
LEFT JOIN tbinstitution AS tbInstSup
ON tbInstSup.Id = tbSup.InstitutionId
LEFT JOIN tbSupervisorOrEvaluator AS tbCoSup
ON tbCoSup.Id = tbFP.CoSupervisorID
LEFT JOIN tbinstitution AS tbInstCoSup
ON tbCoSup.InstitutionId = tbInstCoSup.Id
WHERE tbFP.SupervisorID = 1 or tbFP.CoSupervisorID = 1
ORDER BY tbSt.Name;


SELECT tbFPD.*, tbFP.*, tbSt.*, tbSup.*, tbInstSup.*, tbCoSup.*, tbInstCoSup.*, tbEv1.*, tbInstEv1.*, tbEv2.*, tbInstEv2.*, tbEv3.*, tbInstEv3.*, tbEv4.*, tbInstEv4.*, tbEv5.*, tbInstEv5.*, tbEv6.*, tbInstEv6.*
FROM tbFinalProjectDefense AS tbFPD
JOIN tbFinalProject AS tbFP
ON tbFPD.FinalProjectId = tbFP.Id
JOIN tbStudent AS tbSt
ON tbFP.StudentId = tbSt.Id
LEFT JOIN tbSupervisorOrEvaluator AS tbSup
ON tbSup.Id = tbFP.SupervisorID
LEFT JOIN tbinstitution AS tbInstSup
ON tbInstSup.Id = tbSup.InstitutionId
LEFT JOIN tbSupervisorOrEvaluator AS tbCoSup
ON tbCoSup.Id = tbFP.CoSupervisorID
LEFT JOIN tbinstitution AS tbInstCoSup
ON tbCoSup.InstitutionId = tbInstCoSup.Id
LEFT JOIN tbSupervisorOrEvaluator AS tbEv1
ON tbFPD.Evaluator1Id = tbEv1.Id
LEFT JOIN tbinstitution AS tbInstEv1
ON tbEv1.InstitutionId = tbInstEv1.Id
LEFT JOIN tbSupervisorOrEvaluator AS tbEv2
ON tbFPD.Evaluator2Id = tbEv2.Id
LEFT JOIN tbinstitution AS tbInstEv2
ON tbEv2.InstitutionId = tbInstEv2.Id
LEFT JOIN tbSupervisorOrEvaluator AS tbEv3
ON tbFPD.Evaluator3Id = tbEv3.Id
LEFT JOIN tbinstitution AS tbInstEv3
ON tbEv3.InstitutionId = tbInstEv3.Id
LEFT JOIN tbSupervisorOrEvaluator AS tbEv4
ON tbFPD.Evaluator4Id = tbEv4.Id
LEFT JOIN tbinstitution AS tbInstEv4
ON tbEv4.InstitutionId = tbInstEv4.Id
LEFT JOIN tbSupervisorOrEvaluator AS tbEv5
ON tbFPD.Evaluator5Id = tbEv5.Id
LEFT JOIN tbinstitution AS tbInstEv5
ON tbEv5.InstitutionId = tbInstEv5.Id
LEFT JOIN tbSupervisorOrEvaluator AS tbEv6
ON tbFPD.Evaluator6Id = tbEv6.Id
LEFT JOIN tbinstitution AS tbInstEv6
ON tbEv6.InstitutionId = tbInstEv6.Id
ORDER BY tbSt.Name;
  


SELECT tbFPD.*, tbFP.*, tbSt.*, tbSup.*, tbInstSup.*, tbCoSup.*, tbInstCoSup.*, tbEv1.*, tbInstEv1.*, tbEv2.*, tbInstEv2.*, tbEv3.*, tbInstEv3.*, tbEv4.*, tbInstEv4.*, tbEv5.*, tbInstEv5.*, tbEv6.*, tbInstEv6.*
FROM tbFinalProjectDefense AS tbFPD
JOIN tbFinalProject AS tbFP
ON tbFPD.FinalProjectId = tbFP.Id
JOIN tbStudent AS tbSt
ON tbFP.StudentId = tbSt.Id
LEFT JOIN tbSupervisorOrEvaluator AS tbSup
ON tbSup.Id = tbFP.SupervisorID
LEFT JOIN tbinstitution AS tbInstSup
ON tbInstSup.Id = tbSup.InstitutionId
LEFT JOIN tbSupervisorOrEvaluator AS tbCoSup
ON tbCoSup.Id = tbFP.CoSupervisorID
LEFT JOIN tbinstitution AS tbInstCoSup
ON tbCoSup.InstitutionId = tbInstCoSup.Id
LEFT JOIN tbSupervisorOrEvaluator AS tbEv1
ON tbFPD.Evaluator1Id = tbEv1.Id
LEFT JOIN tbinstitution AS tbInstEv1
ON tbEv1.InstitutionId = tbInstEv1.Id
LEFT JOIN tbSupervisorOrEvaluator AS tbEv2
ON tbFPD.Evaluator2Id = tbEv2.Id
LEFT JOIN tbinstitution AS tbInstEv2
ON tbEv2.InstitutionId = tbInstEv2.Id
LEFT JOIN tbSupervisorOrEvaluator AS tbEv3
ON tbFPD.Evaluator3Id = tbEv3.Id
LEFT JOIN tbinstitution AS tbInstEv3
ON tbEv3.InstitutionId = tbInstEv3.Id
LEFT JOIN tbSupervisorOrEvaluator AS tbEv4
ON tbFPD.Evaluator4Id = tbEv4.Id
LEFT JOIN tbinstitution AS tbInstEv4
ON tbEv4.InstitutionId = tbInstEv4.Id
LEFT JOIN tbSupervisorOrEvaluator AS tbEv5
ON tbFPD.Evaluator5Id = tbEv5.Id
LEFT JOIN tbinstitution AS tbInstEv5
ON tbEv5.InstitutionId = tbInstEv5.Id
LEFT JOIN tbSupervisorOrEvaluator AS tbEv6
ON tbFPD.Evaluator6Id = tbEv6.Id
LEFT JOIN tbinstitution AS tbInstEv6
ON tbEv6.InstitutionId = tbInstEv6.Id
WHERE tbFP.SupervisorID = 1 or tbFP.CoSupervisorID = 1
ORDER BY tbSt.Name;  
  
  