-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 29, 2017 at 09:24 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `letmeteachdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `name`, `password`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `city`
--

CREATE TABLE `city` (
  `city_id` int(11) NOT NULL,
  `city_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `city`
--

INSERT INTO `city` (`city_id`, `city_name`) VALUES
(1, 'Agra'),
(2, 'Ahmedabad'),
(3, 'Ajmer'),
(4, 'Allahabad'),
(5, 'Amritsar'),
(6, 'Asansol'),
(7, 'Bangalore'),
(8, 'Belagaum'),
(9, 'Bhavnagar'),
(10, 'Bhilainagar'),
(11, 'Bhopal'),
(12, 'Bhubaneswar'),
(13, 'Bikaner'),
(14, 'Chandigarh'),
(15, 'Chennai'),
(16, 'Coimbatore'),
(17, 'Cuttack'),
(18, 'Dehradun'),
(19, 'Delhi-Ncr'),
(20, 'Durgapar(West Bengal)'),
(21, 'Goa'),
(22, 'Gorakhpur'),
(23, 'Gulbarga'),
(24, 'Guntur'),
(25, 'Guwahati'),
(26, 'Gwalior'),
(27, 'Hyderabad'),
(28, 'Indore'),
(29, 'Jaipur'),
(30, 'Jalandhar'),
(31, 'Jammu'),
(32, 'Jamnagar'),
(33, 'Jamshedpur'),
(34, 'Jhansi'),
(35, 'Jodhpur'),
(36, 'Kanpur'),
(37, 'Kochi'),
(38, 'Kolhapur'),
(39, 'Kolkata'),
(40, 'Kota'),
(41, 'Lucknow'),
(42, 'Ludhiana'),
(43, 'Madurai'),
(44, 'Mangalore'),
(45, 'Moradabad'),
(46, 'Mumbai'),
(47, 'Mysore'),
(48, 'Nagpur'),
(49, 'Nellore'),
(50, 'Patna'),
(51, 'Pune'),
(52, 'Raipur'),
(53, 'Rajkot'),
(54, 'Ranchi'),
(55, 'Saharanpur'),
(56, 'Salem'),
(57, 'Siliguri'),
(58, 'Solapur'),
(59, 'Sonepath(Haryana)'),
(60, 'Surat'),
(61, 'Tiruchirapalli'),
(62, 'Udaipur'),
(63, 'Ujjain'),
(64, 'Varanasi'),
(65, 'Vijaywada'),
(66, 'Visakhapatnam'),
(67, 'Warangal'),
(68, ''),
(69, 'gfhjghjg');

-- --------------------------------------------------------

--
-- Table structure for table `class`
--

CREATE TABLE `class` (
  `class_id` int(11) NOT NULL,
  `class_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `class`
--

INSERT INTO `class` (`class_id`, `class_name`) VALUES
(2, 'B.A. Idrgfcgf'),
(3, 'B.A. I (H)'),
(4, 'B.A. II'),
(5, 'B.A. II (H)'),
(6, 'B.A. III'),
(7, 'B.A. III (H)'),
(8, 'B.Com I'),
(9, 'B.Com I (H)'),
(10, 'B.Com II'),
(11, 'B.Com II (H)'),
(12, 'B.Com III'),
(13, 'B.Com III (H)'),
(14, 'B.E.'),
(15, 'B.Sc. I'),
(16, 'B.Sc. I (H)'),
(17, 'B.Sc. II (H)'),
(18, 'B.Sc. III (H)'),
(19, 'B. TECH'),
(20, 'BBA'),
(21, 'BCA'),
(22, 'C A'),
(23, 'C.A. (Final)'),
(24, 'C.A. (Inter)'),
(25, 'C.s.'),
(26, 'Class I'),
(27, 'Class II'),
(28, 'Class III'),
(29, 'Class IV'),
(30, 'Class IX'),
(31, 'Class V'),
(32, 'Class VI'),
(33, 'Class VI-X'),
(34, 'Class VII'),
(35, 'Class VIII'),
(36, 'Class X'),
(37, 'Class XI'),
(38, 'Class XI-XII'),
(39, 'Competitive Exam'),
(40, 'Computer'),
(41, 'Corporate Training'),
(42, 'CPT'),
(43, 'CWA+CS'),
(44, 'Dance'),
(45, 'Dance/Music'),
(46, 'Drawing/Art'),
(47, 'Foreign Language'),
(48, 'GMAT'),
(49, 'Graduation Level'),
(50, 'ICWA'),
(51, 'GRE'),
(52, 'IELTS'),
(53, 'IIT'),
(54, 'IIT/JEE/AIEEE'),
(55, 'Languages'),
(56, 'M.A.'),
(57, 'M.Com'),
(58, 'M.Sc.'),
(59, 'MBA'),
(60, 'MCA'),
(61, 'Music'),
(62, 'Nursary/KG'),
(63, 'PMT / Medical'),
(64, 'Pmt / Medical'),
(65, 'Polytechnic'),
(66, 'SAT'),
(67, 'Spoken English'),
(68, 'TOEFL'),
(69, 'Yoga/Fitness');

-- --------------------------------------------------------

--
-- Table structure for table `qualification`
--

CREATE TABLE `qualification` (
  `quali_id` int(11) NOT NULL,
  `quali_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `qualification`
--

INSERT INTO `qualification` (`quali_id`, `quali_name`) VALUES
(1, 'B. E.'),
(2, 'B. EI.Ed'),
(3, 'B.ED'),
(4, 'BCA'),
(5, 'BCOM'),
(6, 'BSC'),
(7, 'BTECH'),
(8, 'Chartered Accountant(CA)'),
(9, 'Company Secretary'),
(10, 'Diploma in French Language'),
(11, 'Diploma in German Language'),
(12, 'E T E(Elementary Teaching Education)'),
(13, 'Graduate'),
(14, 'LLB'),
(15, 'M Phil'),
(16, 'M Tech'),
(17, 'MA'),
(18, 'MBA'),
(19, 'MBBS'),
(20, 'MCA'),
(21, 'MCOM'),
(22, 'MSC'),
(23, 'Persuing Graduation'),
(24, 'PHD'),
(25, 'Physics Hons'),
(26, 'Post Graduate');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `st_id` int(11) NOT NULL,
  `st_code` varchar(100) NOT NULL,
  `st_username` varchar(100) NOT NULL,
  `st_activateemail` varchar(100) NOT NULL,
  `st_password` varchar(50) NOT NULL,
  `st_name` varchar(100) NOT NULL,
  `st_dob` varchar(100) NOT NULL,
  `st_gander` varchar(20) NOT NULL,
  `st_address` text NOT NULL,
  `st_landmark` text NOT NULL,
  `st_city` varchar(100) NOT NULL,
  `st_zone` varchar(100) NOT NULL,
  `st_subarea` varchar(100) NOT NULL,
  `st_typetutor` text NOT NULL,
  `st_class` varchar(100) NOT NULL,
  `st_subject` varchar(100) NOT NULL,
  `st_medium` varchar(50) NOT NULL,
  `st_board` varchar(50) NOT NULL,
  `st_other` varchar(100) NOT NULL,
  `st_mobieno` varchar(50) NOT NULL,
  `st_landline` varchar(50) NOT NULL,
  `st_otherno` varchar(50) NOT NULL,
  `st_fromtime` varchar(50) NOT NULL,
  `st_totime` varchar(50) NOT NULL,
  `st_fees_hourly` varchar(50) NOT NULL,
  `st_fees_monthly` varchar(50) NOT NULL,
  `st_photo` varchar(100) NOT NULL,
  `st_status` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`st_id`, `st_code`, `st_username`, `st_activateemail`, `st_password`, `st_name`, `st_dob`, `st_gander`, `st_address`, `st_landmark`, `st_city`, `st_zone`, `st_subarea`, `st_typetutor`, `st_class`, `st_subject`, `st_medium`, `st_board`, `st_other`, `st_mobieno`, `st_landline`, `st_otherno`, `st_fromtime`, `st_totime`, `st_fees_hourly`, `st_fees_monthly`, `st_photo`, `st_status`) VALUES
(4, 'LMTS170821092907', 'sireesha', 'sireesha@gmail.com', 'sireesha', 'sireesha achugatla', '17-08-2016', 'Female', 'bnmbnm', 'bmbnmmnb', '66', '70', '', 'Male', 'B.A. I (H), B.A. II, B.A. II (H), B.A. III, B.A. III (H)', 'Accounts, All Subjects, Aptitude Test, Auditing, Bengali Language', 'English', 'CBSE', 'bhmnbmnm', '9701591822', '04052333222', '9874563210', '6 PM', '9 PM', '200', '50000', 'uploads/IMGS129471.png', 1);

-- --------------------------------------------------------

--
-- Table structure for table `subarea`
--

CREATE TABLE `subarea` (
  `area_id` int(11) NOT NULL,
  `area_name` varchar(50) NOT NULL,
  `city_id` int(11) NOT NULL,
  `zone_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `subarea`
--

INSERT INTO `subarea` (`area_id`, `area_name`, `city_id`, `zone_id`) VALUES
(3, 'Anupam Bagh', 1, 1),
(4, 'Arjun Nagar', 1, 1),
(5, 'Baghai', 1, 1),
(6, 'Balhera', 1, 1),
(7, 'Balanganj', 1, 1),
(8, 'Bhikari Pur', 1, 1),
(10, 'Budhana Mustkil', 1, 1),
(11, 'Civil Lines', 1, 1),
(12, 'Dayal Bagh', 1, 1),
(13, 'Dayalbagh', 1, 1),
(14, 'Deori', 1, 1),
(15, 'Dhanupura', 1, 1),
(16, 'Dholpura', 1, 1),
(17, 'Firozabad', 1, 1),
(18, 'G.G Puram', 1, 1),
(19, 'Gandhi Nagar', 1, 1),
(20, 'Ghatwasani', 1, 1),
(21, 'Gudun', 1, 1),
(22, 'Hingot kheria', 1, 1),
(23, 'Humaunpur', 1, 1),
(24, 'Idgah Nagar', 1, 1),
(25, 'Ikaran', 1, 1),
(26, 'Jaupura', 1, 1),
(27, 'Kailashpuri', 1, 1),
(28, 'Kakari', 1, 1),
(29, 'Kala Mahal', 1, 1),
(30, 'Kalwari', 1, 1),
(31, 'Karaudhna', 1, 1),
(32, 'Kaveri Kunj', 1, 1),
(33, 'Khandari', 1, 1),
(34, 'Khaspur Ehtmali', 1, 1),
(35, 'Klishan Bagh', 1, 1),
(36, 'Kothi Meena Bazaar', 1, 1),
(37, 'Kuberpur', 1, 1),
(38, 'Kumkupa', 1, 1),
(39, 'Lajpat Kunj', 1, 1),
(40, 'Laramada', 1, 1),
(41, 'Lohamandi', 1, 1),
(42, 'Madhu Nagar', 1, 1),
(43, 'Mangalam Estate', 1, 1),
(44, 'Mangura', 1, 1),
(45, 'Manoharpur', 1, 1),
(46, 'Mantola', 1, 1),
(47, 'Midhakur', 1, 1),
(48, 'Model Town', 1, 1),
(49, 'Moti Bagh', 1, 1),
(50, 'Nagia Kachhan', 1, 1),
(51, 'Nagla Lalijit', 1, 1),
(52, 'Nagla Padma', 1, 1),
(53, 'Naglapadi', 1, 1),
(54, 'Nainana Brahaman', 1, 1),
(55, 'Nainana Jat', 1, 1),
(56, 'Nanpur', 1, 1),
(57, 'Nawalganj', 1, 1),
(58, 'New Agra', 1, 1),
(59, 'New Agra Colony', 1, 1),
(60, 'Pati Pachagain', 1, 1),
(61, 'Phulpur', 1, 1),
(62, 'Purani Mandi', 1, 1),
(63, 'Raibha', 1, 1),
(64, 'Raipur', 1, 1),
(65, 'Rakabganj', 1, 1),
(66, 'Rasulpur', 1, 1),
(67, 'Rohta', 1, 1),
(68, 'Sadabam Chauatha', 1, 1),
(69, 'Sahara', 1, 1),
(70, 'Salemabad', 1, 1),
(71, 'Shahganj', 1, 1),
(72, 'Shahzadi Mandi', 1, 1),
(73, 'Shamsabad', 1, 1),
(74, 'Shyam Nagar', 1, 1),
(75, 'Sikanadara', 1, 1),
(76, 'Soam Bagh', 1, 1),
(77, 'Sunari', 1, 1),
(78, 'Surehra', 1, 1),
(79, 'Tajganj', 1, 1),
(80, 'Tapa Khurd', 1, 1),
(81, 'Teal Semri', 1, 1),
(82, 'Tikri', 1, 1),
(83, 'Tondra', 1, 1),
(84, 'Tundla', 1, 1),
(85, 'Undra', 1, 1),
(86, 'Vibhab Nagar', 1, 1),
(87, 'Vidya Nagar', 1, 1),
(88, 'Wazirpura', 1, 1),
(89, 'Adalaj', 2, 2),
(90, 'Adraj Moti', 2, 2),
(91, 'Ambapur', 2, 2),
(92, 'Ambli', 2, 2),
(93, 'Amraiwadi', 2, 2),
(94, 'Amroli', 2, 2),
(95, 'Asarwa', 2, 2),
(96, 'Aslali', 2, 2),
(97, 'Ayojan Nagar', 2, 2),
(98, 'Bahyal', 2, 2),
(99, 'Bakroi Bujrang', 2, 2),
(100, 'Basant Nagar', 2, 2),
(101, 'Bhadra', 2, 2),
(102, 'Bhoyan Rathod', 2, 2),
(103, 'Bodakdev', 2, 2),
(104, 'Bol', 2, 2),
(105, 'Bopal', 2, 2),
(106, 'Cantonment', 2, 2),
(107, 'Chandkheda', 2, 2),
(108, 'Cheyrasar', 2, 2),
(109, 'Chharodi', 2, 2),
(110, 'Chhipdi', 2, 2),
(111, 'D-Cabin', 2, 2),
(112, 'Dhanaj', 2, 2),
(113, 'Dharoda', 2, 2),
(114, 'Doder', 2, 2),
(115, 'Ghodasar', 2, 2),
(116, 'Ghuma', 2, 2),
(117, 'Giramtha', 2, 2),
(118, 'Godasar', 2, 2),
(119, 'Godhavi', 2, 2),
(120, 'Indra Nagarszfds sdzfsdfs', 3, 15);

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

CREATE TABLE `subject` (
  `subj_id` int(11) NOT NULL,
  `subj_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `subject`
--

INSERT INTO `subject` (`subj_id`, `subj_name`) VALUES
(1, 'Accounts'),
(2, 'All Subjects'),
(3, 'Aptitude Test'),
(4, 'Auditing'),
(5, 'Automata'),
(6, 'Bengali Language'),
(7, 'Bio Technology'),
(8, 'Biology'),
(9, 'Botany'),
(10, 'Business Law'),
(11, 'Business Maths'),
(12, 'Business Studies'),
(13, 'C++'),
(14, 'Chemistry'),
(15, 'Chinese'),
(16, 'Civics'),
(17, 'Company Law'),
(18, 'Company'),
(19, 'Costing'),
(20, 'Dance'),
(21, 'Discreate Math'),
(22, 'Drawing / Painting'),
(23, 'Econometrics'),
(24, 'Economics'),
(25, 'Electronics & Communication'),
(26, 'Engineering Drawing'),
(27, 'English'),
(28, 'EVS'),
(29, 'financial Management'),
(30, 'french'),
(31, 'G MAT'),
(32, 'Geography'),
(33, 'German'),
(34, 'Graphic Designing'),
(35, 'Guitar'),
(36, 'Hindi'),
(37, 'History'),
(38, 'Income Tax/Taxation'),
(39, 'Informatic Pratices'),
(40, 'Italian'),
(41, 'Japanese'),
(42, 'Java'),
(43, 'Korean'),
(44, 'Law'),
(45, 'Logical Reasoning'),
(46, 'Management'),
(47, 'Mathematics'),
(48, 'Marketing'),
(49, 'Mass Communication'),
(50, 'Maths+Science'),
(51, 'Microbiology'),
(52, 'Multi Media'),
(53, 'Music'),
(54, 'Nutrition/Food Science'),
(55, 'Operational Research'),
(56, 'Philosophy'),
(57, 'Physics'),
(58, 'Physiology'),
(59, 'Piano/Guitar'),
(60, 'Polytechnic'),
(61, 'Portuguese Language'),
(62, 'Psychology'),
(63, 'Punjabi'),
(64, 'Russian'),
(65, 'Sanskrit'),
(66, 'Science'),
(67, 'Social Studies'),
(68, 'Sociology'),
(69, 'Spanish'),
(70, 'Special Educator'),
(71, 'Spoken English'),
(72, 'Statistics'),
(73, 'Yoga'),
(74, 'Zoology');

-- --------------------------------------------------------

--
-- Table structure for table `teach_location`
--

CREATE TABLE `teach_location` (
  `teach_id` int(11) NOT NULL,
  `teach_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `teach_location`
--

INSERT INTO `teach_location` (`teach_id`, `teach_name`) VALUES
(4, 'Arjun Nagarcx vfcxbvcf'),
(5, 'Baghaidfdgd'),
(6, 'Balhera'),
(7, 'Belanganj'),
(8, 'Bhikari Pur'),
(9, 'Bhur Ka Bagh'),
(10, 'Budhana Mustkil'),
(11, 'Civil Lines'),
(12, 'Dayal Bagh'),
(13, 'Deori'),
(14, 'Dhanupura'),
(15, 'Dholpur'),
(16, 'Firozabad'),
(17, 'G.G Puram'),
(18, 'Gandhi Nagar'),
(19, 'Ghatwasani'),
(20, 'Gudun'),
(21, 'Hingot Kheria'),
(22, 'Idgah Nagar'),
(23, 'Ikaran'),
(24, 'Jaupura'),
(25, 'Kailashpuri'),
(26, 'Kakari'),
(27, 'Kala Mahal'),
(28, 'Kalwari'),
(29, 'Karaudhna'),
(30, 'Kaveri Kunj'),
(31, 'Khandari'),
(32, 'Khaspur Ehtmali'),
(33, 'Klishan Bagh'),
(34, 'Kothi Meena Bazaar'),
(35, 'Kuberpur'),
(36, 'Kumkupa'),
(37, 'Lajpat Kunj'),
(38, 'Laramada'),
(39, 'Lohamandi'),
(40, 'Mangalam Estate'),
(41, 'Mangura'),
(42, 'Manoharpur'),
(43, 'Mantola'),
(44, 'Mldhakur'),
(45, 'Model Town'),
(46, 'Moti Bagh'),
(47, 'Nagia Kachhan'),
(48, 'Nagla Lalijit'),
(49, 'Nagla Padma'),
(50, 'Naglapadi'),
(51, 'Nainana Brahaman'),
(52, 'Nainana Jat'),
(53, 'Nanpur'),
(54, 'Nawalganj'),
(55, 'New Agra'),
(56, 'New Agra Colony'),
(57, 'Pati Pachgain'),
(58, 'Phulpur'),
(59, 'Purani Mandi'),
(60, 'Raibha'),
(61, 'Raipur'),
(62, 'Rakabganj'),
(63, 'Rasulpur'),
(64, 'Sadabam Chauatha'),
(65, 'Sahara'),
(66, 'Salemabad'),
(67, 'Shahganj'),
(68, 'Shahzadi Mandi'),
(69, 'Shamsabad'),
(70, 'Shyam Nagar'),
(71, 'Sikanadara'),
(72, 'Soam Bagh'),
(73, 'Sunari'),
(74, 'Tajganj'),
(75, 'Tapa Khurd'),
(76, 'Teal Semri'),
(77, 'Tikri'),
(78, 'Tondra'),
(79, 'Tundla'),
(80, 'Undra'),
(81, 'Vibhab Nagar'),
(82, 'Vidya Nagar'),
(83, 'Wazirpura'),
(85, 'safsdzfgds');

-- --------------------------------------------------------

--
-- Table structure for table `time`
--

CREATE TABLE `time` (
  `time_id` int(11) NOT NULL,
  `from_time` varchar(50) NOT NULL,
  `to_time` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `time`
--

INSERT INTO `time` (`time_id`, `from_time`, `to_time`) VALUES
(2, '10 AMsefseg', '11 AMszfsgf'),
(3, '11 AM', '12 PM'),
(4, '12 PM', '1 PM'),
(5, '1 PM', '2 PM'),
(6, '2 PM', '3 PM'),
(7, '3 PM', '4 PM'),
(8, '4 PM', '5 PM'),
(9, '5 PM', '6 PM'),
(10, '6 PM', '7 PM'),
(11, '7 PM', '8 PM'),
(12, '8 PM', '9 PM'),
(13, '10 awjfsf', 'sdfgdsg');

-- --------------------------------------------------------

--
-- Table structure for table `tutors`
--

CREATE TABLE `tutors` (
  `t_id` int(11) NOT NULL,
  `t_code` varchar(100) NOT NULL,
  `t_username` varchar(100) NOT NULL,
  `t_activateemail` varchar(100) NOT NULL,
  `t_password` varchar(50) NOT NULL,
  `t_name` varchar(100) NOT NULL,
  `t_dob` varchar(100) NOT NULL,
  `t_gander` varchar(20) NOT NULL,
  `t_address` text NOT NULL,
  `t_landmark` text NOT NULL,
  `t_city` varchar(100) NOT NULL,
  `t_zone` varchar(100) NOT NULL,
  `t_subarea` varchar(100) NOT NULL,
  `t_teaching` varchar(100) NOT NULL,
  `t_class` varchar(100) NOT NULL,
  `t_teachlocation` varchar(100) NOT NULL,
  `t_subject` varchar(100) NOT NULL,
  `t_contactno` varchar(30) NOT NULL,
  `t_fromtime` varchar(50) NOT NULL,
  `t_totime` varchar(50) NOT NULL,
  `t_experience` varchar(100) NOT NULL,
  `t_qualification` varchar(100) NOT NULL,
  `t_fees_hourly` varchar(50) NOT NULL,
  `t_fees_monthly` varchar(50) NOT NULL,
  `t_photo` varchar(50) NOT NULL,
  `t_status` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tutors`
--

INSERT INTO `tutors` (`t_id`, `t_code`, `t_username`, `t_activateemail`, `t_password`, `t_name`, `t_dob`, `t_gander`, `t_address`, `t_landmark`, `t_city`, `t_zone`, `t_subarea`, `t_teaching`, `t_class`, `t_teachlocation`, `t_subject`, `t_contactno`, `t_fromtime`, `t_totime`, `t_experience`, `t_qualification`, `t_fees_hourly`, `t_fees_monthly`, `t_photo`, `t_status`) VALUES
(1, 'LMTT899294', 'sireesha', 'sireesha.achugatla@gmail.com', 'sireesha', 'Sireesha Achugatla', '17-08-2017', 'Female', 'indira nagar, Hyderabad', 'sefsd dgfthgf', '4', '4', '', 'Home Tuition', 'B.A. I (H), B.A. II, B.A. II (H), B.A. III, B.A. III (H)', 'Baghaidfdgd, Balhera, Budhana Mustkil, Civil Lines, Dayal Bagh', 'Accounts, All Subjects, Aptitude Test, Auditing, Automata', '9701591822', '11 AM', '2 PM', '48 year(s)', 'B. E., B. EI.Ed, B.ED, BCA, BCOM', '5000', '20000', 'uploads/IMGS129471.png', 1),
(2, 'LMTT20170819091645', 'nikitha', 'nikitha.achugatla@gmail.com', 'sireesha', '', '', '', '', '', '63', '67', '', 'Home Tuition', 'B.A. II (H), B.A. III', 'Balhera, Belanganj, Bhikari Pur', 'Aptitude Test, Auditing, Automata', '78965413301', '8 PM', '7 PM', '10 year(s)', 'B. E., B. EI.Ed, B.ED, BCA', '200', '500', 'uploads/IMGS129471.png', 1),
(4, 'LMTT170819115242', 'srikanth', 'srikanth.achugatla@gmail.com', 'sireesha', 'srikanth122', '19-08-2017', 'Male', 'indira nagar', 'hyderabad', '1', '1', '7', 'Home Tuition', 'B.A. I (H), B.A. II, B.A. II (H), B.A. III, B.A. III (H)', 'Balhera, Belanganj, Bhikari Pur, Bhur Ka Bagh, Budhana Mustkil', 'Aptitude Test, Auditing, Automata, Bengali Language, Bio Technology', '970159182', '6 PM', '9 PM', '12 year(s)', 'B. E., B. EI.Ed, B.ED, BCA, BCOM', '200', '2000', 'uploads/IMGS129471.png', 1);

-- --------------------------------------------------------

--
-- Table structure for table `zone`
--

CREATE TABLE `zone` (
  `zone_id` int(11) NOT NULL,
  `zone_name` varchar(50) NOT NULL,
  `city_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `zone`
--

INSERT INTO `zone` (`zone_id`, `zone_name`, `city_id`) VALUES
(1, 'Agra', 1),
(2, 'Ahmedabad', 2),
(3, 'Ajmer', 3),
(4, 'Allahabad', 4),
(5, 'Amritsar', 5),
(6, 'Asansol', 6),
(7, 'Bangalore', 7),
(8, 'Belagaum', 8),
(9, 'Bhavnagar', 9),
(10, 'Bhilainagar', 10),
(11, 'Bhopal', 11),
(12, 'Bhubaneswar', 12),
(13, 'Bikaner', 13),
(14, 'Chandigarh', 14),
(15, 'Chennai', 15),
(16, 'Coimbatore', 16),
(17, 'Cuttack', 17),
(18, 'Dehradun', 18),
(19, 'New Delhi', 19),
(20, 'Gurgaon', 19),
(21, 'Faridabad', 19),
(22, 'Ghaziabad', 19),
(23, 'Noida', 19),
(24, 'Durgapar, West Bengal', 20),
(25, 'Goa', 21),
(26, 'Gorakhpur', 22),
(27, 'Gulbarga', 23),
(28, 'Guntur', 24),
(29, 'Guwahati', 25),
(30, 'Gwalior', 26),
(31, 'Hyderabad', 27),
(32, 'Indore', 28),
(33, 'Jaipur', 29),
(34, 'Jalandhar', 30),
(35, 'Jammu', 31),
(36, 'Jamnagar', 32),
(37, 'Jamshedpur', 33),
(38, 'Jhansi', 34),
(39, 'Jodhpur', 35),
(40, 'Kanpur', 36),
(41, 'Kochi', 37),
(42, 'Kolhapur', 38),
(43, 'Kolkata', 39),
(44, 'Kota', 40),
(45, 'Lucknow', 41),
(46, 'Ludhiana', 42),
(47, 'Madurai', 43),
(48, 'Mangalore', 44),
(49, 'Moradabad', 45),
(50, 'Mumbai', 46),
(51, 'Mysore', 47),
(52, 'Nagpur', 48),
(53, 'Nellore', 49),
(54, 'Patna', 50),
(55, 'Pune', 51),
(56, 'Raipur', 52),
(57, 'Rajkot', 53),
(58, 'Ranchi', 54),
(59, 'Saharanpur', 55),
(60, 'Salem', 56),
(61, 'Siliguri', 57),
(62, 'Solapur', 58),
(63, 'Sonepath(Haryana)', 59),
(64, 'Surat', 60),
(65, 'Tiruchirapalli', 61),
(66, 'Udaipur', 62),
(67, 'Ujjain', 63),
(68, 'Varanasi', 64),
(69, 'Vijaywada', 65),
(70, 'Visakhapatnam', 66),
(71, 'Warangal', 67);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `city`
--
ALTER TABLE `city`
  ADD PRIMARY KEY (`city_id`);

--
-- Indexes for table `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`class_id`);

--
-- Indexes for table `qualification`
--
ALTER TABLE `qualification`
  ADD PRIMARY KEY (`quali_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`st_id`);

--
-- Indexes for table `subarea`
--
ALTER TABLE `subarea`
  ADD PRIMARY KEY (`area_id`);

--
-- Indexes for table `subject`
--
ALTER TABLE `subject`
  ADD PRIMARY KEY (`subj_id`);

--
-- Indexes for table `teach_location`
--
ALTER TABLE `teach_location`
  ADD PRIMARY KEY (`teach_id`);

--
-- Indexes for table `time`
--
ALTER TABLE `time`
  ADD PRIMARY KEY (`time_id`);

--
-- Indexes for table `tutors`
--
ALTER TABLE `tutors`
  ADD PRIMARY KEY (`t_id`);

--
-- Indexes for table `zone`
--
ALTER TABLE `zone`
  ADD PRIMARY KEY (`zone_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `city`
--
ALTER TABLE `city`
  MODIFY `city_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;
--
-- AUTO_INCREMENT for table `class`
--
ALTER TABLE `class`
  MODIFY `class_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;
--
-- AUTO_INCREMENT for table `qualification`
--
ALTER TABLE `qualification`
  MODIFY `quali_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `st_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `subarea`
--
ALTER TABLE `subarea`
  MODIFY `area_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=121;
--
-- AUTO_INCREMENT for table `subject`
--
ALTER TABLE `subject`
  MODIFY `subj_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=75;
--
-- AUTO_INCREMENT for table `teach_location`
--
ALTER TABLE `teach_location`
  MODIFY `teach_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=86;
--
-- AUTO_INCREMENT for table `time`
--
ALTER TABLE `time`
  MODIFY `time_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `tutors`
--
ALTER TABLE `tutors`
  MODIFY `t_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `zone`
--
ALTER TABLE `zone`
  MODIFY `zone_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
