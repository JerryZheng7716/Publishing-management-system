USE [master]
GO
/****** Object:  Database [Publisher]    Script Date: 2018/10/9 下午7:32:37 ******/
CREATE DATABASE [Publisher]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Publisher', FILENAME = N'c:\DB\Publisher.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'Publisher_log', FILENAME = N'c:\DB\Publisher_log.ldf' , SIZE = 1792KB , MAXSIZE = 204800KB , FILEGROWTH = 10%)
GO
ALTER DATABASE [Publisher] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Publisher].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Publisher] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Publisher] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Publisher] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Publisher] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Publisher] SET ARITHABORT OFF 
GO
ALTER DATABASE [Publisher] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Publisher] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Publisher] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Publisher] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Publisher] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Publisher] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Publisher] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Publisher] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Publisher] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Publisher] SET  ENABLE_BROKER 
GO
ALTER DATABASE [Publisher] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Publisher] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Publisher] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Publisher] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Publisher] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Publisher] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Publisher] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Publisher] SET RECOVERY FULL 
GO
ALTER DATABASE [Publisher] SET  MULTI_USER 
GO
ALTER DATABASE [Publisher] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Publisher] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Publisher] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Publisher] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Publisher] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'Publisher', N'ON'
GO
ALTER DATABASE [Publisher] SET QUERY_STORE = OFF
GO
USE [Publisher]
GO
ALTER DATABASE SCOPED CONFIGURATION SET IDENTITY_CACHE = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET LEGACY_CARDINALITY_ESTIMATION = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET MAXDOP = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET PARAMETER_SNIFFING = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET QUERY_OPTIMIZER_HOTFIXES = PRIMARY;
GO
USE [Publisher]
GO
/****** Object:  Table [dbo].[Accounts]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Accounts](
	[accId] [int] IDENTITY(0,1) NOT NULL,
	[ordNo] [char](11) NULL,
	[selNo] [char](5) NULL,
	[ordPayment] [float] NOT NULL,
	[accPayment] [float] NOT NULL,
	[ordTap] [char](1) NULL,
	[prtRematk] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[accId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Authored]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Authored](
	[bkNo] [char](5) NOT NULL,
	[auNo] [char](6) NOT NULL,
	[auOrder] [smallint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[bkNo] ASC,
	[auNo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Authors]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Authors](
	[auNo] [char](6) NOT NULL,
	[auName] [char](10) NOT NULL,
	[auSex] [bit] NOT NULL,
	[auTitle] [char](10) NULL,
	[auTelephone] [char](13) NOT NULL,
	[auProvince] [char](10) NULL,
	[auCity] [char](10) NULL,
	[auZip] [char](6) NULL,
	[auAddress] [varchar](50) NULL,
	[auEmail] [varchar](50) NULL,
	[auRemark] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[auNo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Books]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Books](
	[bkNo] [char](5) NOT NULL,
	[bkTitle] [varchar](30) NOT NULL,
	[bkPrice] [smallint] NOT NULL,
	[bkWords] [int] NULL,
	[bkFirstTime] [date] NULL,
	[bkLastNumber] [smallint] NULL,
	[bkPrtQty] [int] NULL,
	[whNo] [char](2) NULL,
PRIMARY KEY CLUSTERED 
(
	[bkNo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BookType]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BookType](
	[btId] [int] IDENTITY(0,1) NOT NULL,
	[typeNo] [char](2) NULL,
	[bkNo] [char](5) NULL,
PRIMARY KEY CLUSTERED 
(
	[btId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Contracts]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Contracts](
	[conNo] [char](11) NOT NULL,
	[bkNo] [char](5) NULL,
	[conTime] [char](10) NOT NULL,
	[auNo] [char](6) NULL,
	[empNo] [char](5) NULL,
	[conNumber] [int] NULL,
	[conRoyalty] [int] NULL,
	[conPay] [smallint] NULL,
	[conRemark] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[conNo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Departments]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Departments](
	[deptNO] [char](2) NOT NULL,
	[deptTitle] [char](10) NOT NULL,
	[deptManager] [char](5) NULL,
	[deptTelephone] [char](13) NULL,
	[deptAddress] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[deptNO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Employee]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employee](
	[empNo] [char](5) NOT NULL,
	[empName] [char](10) NOT NULL,
	[empSex] [bit] NOT NULL,
	[empBirthday] [date] NULL,
	[empEntrytime] [date] NULL,
	[empProvince] [char](20) NULL,
	[empCity] [char](20) NULL,
	[empempZip] [char](6) NULL,
	[empAdress] [varchar](50) NULL,
	[empTelephone] [char](13) NOT NULL,
	[empEmail] [varchar](50) NULL,
	[deptNo] [char](2) NULL,
	[empPwd] [char](40) NOT NULL,
	[empAuthority] [char](8) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[empNo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[InoutWH]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[InoutWH](
	[ioId] [int] IDENTITY(0,1) NOT NULL,
	[whNo] [char](2) NULL,
	[bkNo] [char](5) NULL,
	[ioType] [char](1) NULL,
	[ioTime] [date] NULL,
	[ioQuantity] [int] NOT NULL,
	[empNo] [char](5) NULL,
	[prtRemark] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[ioId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetails]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetails](
	[odId] [int] IDENTITY(0,1) NOT NULL,
	[ordNo] [char](11) NULL,
	[bkNo] [char](5) NULL,
	[odQuantity] [int] NOT NULL,
	[prtNumber] [smallint] NULL,
PRIMARY KEY CLUSTERED 
(
	[odId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[ordNo] [char](11) NOT NULL,
	[selNo] [char](5) NULL,
	[empNo] [char](5) NULL,
	[ordTime] [datetime] NULL,
	[ordSendtime] [date] NULL,
	[ordQuantity] [int] NOT NULL,
	[ordPayment] [float] NOT NULL,
	[ordDiscount] [float] NULL,
	[prtRemark] [varchar](max) NULL,
	[ordState] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ordNo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OutOfStock]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OutOfStock](
	[oosId] [int] IDENTITY(0,1) NOT NULL,
	[bkNo] [char](5) NULL,
	[oosQuantity] [int] NOT NULL,
	[empNo] [char](5) NULL,
	[oosTime] [datetime] NULL,
	[oosUse] [varchar](max) NULL,
	[oosState] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[oosId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PubPrint]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PubPrint](
	[prtID] [int] IDENTITY(1,1) NOT NULL,
	[bkNo] [char](5) NULL,
	[prtTime] [date] NOT NULL,
	[prtQuantity] [int] NOT NULL,
	[prtNumber] [smallint] NULL,
	[empNo] [char](5) NULL,
	[prtRemark] [varchar](max) NULL,
	[prtState] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[prtID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Sellers]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sellers](
	[selNo] [char](5) NOT NULL,
	[selTitle] [char](10) NOT NULL,
	[selName] [char](10) NOT NULL,
	[selTelephone] [char](13) NOT NULL,
	[selProvince] [char](20) NULL,
	[selCity] [char](20) NULL,
	[selZip] [char](6) NULL,
	[selAddress] [varchar](50) NULL,
	[selEmail] [varchar](50) NULL,
	[selDeliAddress] [varchar](50) NULL,
	[selRemark] [varchar](max) NULL,
	[selPwd] [char](40) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[selNo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[types]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[types](
	[typeNo] [char](2) NOT NULL,
	[typeTitle] [char](10) NULL,
	[typeRemark] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[typeNo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Warehouse]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Warehouse](
	[whNo] [char](2) NOT NULL,
	[empNo] [char](5) NULL,
	[whTelephone] [char](13) NULL,
	[whAddress] [char](50) NULL,
	[whArea] [int] NULL,
	[whRemark] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[whNo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00001', N'010001', 1)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00002', N'290011', 1)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00003', N'010001', 1)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00003', N'030212', 2)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00003', N'110001', 3)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00003', N'110212', 4)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00004', N'010001', 3)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00004', N'010011', 1)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00004', N'030212', 4)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00004', N'110212', 2)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00005', N'010004', 3)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00005', N'010006', 2)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00005', N'010008', 1)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00006', N'010003', 1)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00007', N'290004', 1)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00008', N'010004', 1)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00009', N'010004', 1)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00010', N'010005', 1)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'00011', N'010006', 1)
INSERT [dbo].[Authored] ([bkNo], [auNo], [auOrder]) VALUES (N'13130', N'010008', 1)
INSERT [dbo].[Authors] ([auNo], [auName], [auSex], [auTitle], [auTelephone], [auProvince], [auCity], [auZip], [auAddress], [auEmail], [auRemark]) VALUES (N'010001', N'匡芳君    ', 0, N'教授      ', N'12222222222  ', N'北京市    ', N'温州      ', N'222323', N'温州商学院', N'zh_jl@qq.com', N'温州商学院计算机系主任')
INSERT [dbo].[Authors] ([auNo], [auName], [auSex], [auTitle], [auTelephone], [auProvince], [auCity], [auZip], [auAddress], [auEmail], [auRemark]) VALUES (N'010003', N'马格利特  ', 0, N'教授      ', N'12222222222  ', N'北京市    ', N'温州      ', N'222323', N'温州商学院', N'zh_jl@qq.com', N'著名作家')
INSERT [dbo].[Authors] ([auNo], [auName], [auSex], [auTitle], [auTelephone], [auProvince], [auCity], [auZip], [auAddress], [auEmail], [auRemark]) VALUES (N'010004', N'渡边淳一  ', 0, N'教授      ', N'12222222222  ', N'北京市    ', N'温州      ', N'222323', N'温州商学院', N'zh_jl@qq.com', N'著名作家')
INSERT [dbo].[Authors] ([auNo], [auName], [auSex], [auTitle], [auTelephone], [auProvince], [auCity], [auZip], [auAddress], [auEmail], [auRemark]) VALUES (N'010005', N'钱钟书    ', 0, N'教授      ', N'12222222222  ', N'北京市    ', N'温州      ', N'222323', N'温州商学院', N'zh_jl@qq.com', N'著名作家')
INSERT [dbo].[Authors] ([auNo], [auName], [auSex], [auTitle], [auTelephone], [auProvince], [auCity], [auZip], [auAddress], [auEmail], [auRemark]) VALUES (N'010006', N'劳伦斯    ', 0, N'教授      ', N'12222222222  ', N'北京市    ', N'温州      ', N'222323', N'温州商学院', N'zh_jl@qq.com', N'著名作家')
INSERT [dbo].[Authors] ([auNo], [auName], [auSex], [auTitle], [auTelephone], [auProvince], [auCity], [auZip], [auAddress], [auEmail], [auRemark]) VALUES (N'010007', N'泰戈尔    ', 0, N'教授      ', N'12222222222  ', N'北京市    ', N'温州      ', N'222323', N'温州商学院', N'zh_jl@qq.com', N'著名作家')
INSERT [dbo].[Authors] ([auNo], [auName], [auSex], [auTitle], [auTelephone], [auProvince], [auCity], [auZip], [auAddress], [auEmail], [auRemark]) VALUES (N'010008', N'刘慈欣    ', 1, N'教授      ', N'12222222222  ', N'北京市    ', N'温州      ', N'222323', N'温州商学院', N'zh_jl@qq.com', N'著名作家')
INSERT [dbo].[Authors] ([auNo], [auName], [auSex], [auTitle], [auTelephone], [auProvince], [auCity], [auZip], [auAddress], [auEmail], [auRemark]) VALUES (N'010011', N'霍金      ', 1, N'教授      ', N'12222222222  ', N'北京市    ', N'温州      ', N'222323', N'温州商学院', N'zh_jl@qq.com', N'著名作家')
INSERT [dbo].[Authors] ([auNo], [auName], [auSex], [auTitle], [auTelephone], [auProvince], [auCity], [auZip], [auAddress], [auEmail], [auRemark]) VALUES (N'030212', N'郑健磊    ', 1, N'学生      ', N'12222222222  ', N'上海市    ', N'温州      ', N'222222', N'温州商学院', N'zh_jl@qq.com', N'温州商学院学生')
INSERT [dbo].[Authors] ([auNo], [auName], [auSex], [auTitle], [auTelephone], [auProvince], [auCity], [auZip], [auAddress], [auEmail], [auRemark]) VALUES (N'110001', N'张学友    ', 1, N'副教授    ', N'12222222222  ', N'浙江省    ', N'温州      ', N'222323', N'温州商学院', N'zh_jl@qq.com', N'')
INSERT [dbo].[Authors] ([auNo], [auName], [auSex], [auTitle], [auTelephone], [auProvince], [auCity], [auZip], [auAddress], [auEmail], [auRemark]) VALUES (N'110212', N'涂嘉庆    ', 1, N'副教授    ', N'12222222222  ', N'浙江省    ', N'温州      ', N'222222', N'温州商学院', N'zh_jl@qq.com', N'信息工程学院副院长')
INSERT [dbo].[Authors] ([auNo], [auName], [auSex], [auTitle], [auTelephone], [auProvince], [auCity], [auZip], [auAddress], [auEmail], [auRemark]) VALUES (N'280001', N'匡芳君    ', 0, N'教授      ', N'12222222222  ', N'广西      ', N'温州      ', N'222323', N'温州商学院', N'zh_jl@qq.com', N'温州商学院计算机系主任')
INSERT [dbo].[Authors] ([auNo], [auName], [auSex], [auTitle], [auTelephone], [auProvince], [auCity], [auZip], [auAddress], [auEmail], [auRemark]) VALUES (N'280003', N'张爱玲    ', 0, N'教授      ', N'12222222222  ', N'北京市    ', N'温州      ', N'222323', N'温州商学院', N'zh_jl@qq.com', N'著名作家')
INSERT [dbo].[Authors] ([auNo], [auName], [auSex], [auTitle], [auTelephone], [auProvince], [auCity], [auZip], [auAddress], [auEmail], [auRemark]) VALUES (N'290004', N'村上春树  ', 0, N'教授      ', N'12222222222  ', N'西藏      ', N'温州      ', N'222323', N'温州商学院', N'zh_jl@qq.com', N'著名作家')
INSERT [dbo].[Authors] ([auNo], [auName], [auSex], [auTitle], [auTelephone], [auProvince], [auCity], [auZip], [auAddress], [auEmail], [auRemark]) VALUES (N'290011', N'罗贯中    ', 0, N'教授      ', N'12222222222  ', N'西藏      ', N'温州      ', N'222323', N'温州商学院', N'zh_jl@qq.com', N'著名作家')
INSERT [dbo].[Books] ([bkNo], [bkTitle], [bkPrice], [bkWords], [bkFirstTime], [bkLastNumber], [bkPrtQty], [whNo]) VALUES (N'00001', N'隋唐演义', 132, 123, CAST(N'2005-01-01' AS Date), 11, 9497, N'02')
INSERT [dbo].[Books] ([bkNo], [bkTitle], [bkPrice], [bkWords], [bkFirstTime], [bkLastNumber], [bkPrtQty], [whNo]) VALUES (N'00002', N'水浒传', 34, 123, CAST(N'1990-09-07' AS Date), 3, 1000, N'31')
INSERT [dbo].[Books] ([bkNo], [bkTitle], [bkPrice], [bkWords], [bkFirstTime], [bkLastNumber], [bkPrtQty], [whNo]) VALUES (N'00003', N'语文教材', 21, 32000, CAST(N'2005-01-01' AS Date), 1, 1000822, N'02')
INSERT [dbo].[Books] ([bkNo], [bkTitle], [bkPrice], [bkWords], [bkFirstTime], [bkLastNumber], [bkPrtQty], [whNo]) VALUES (N'00004', N'果壳中的宇宙-彩图版', 213, 12000, CAST(N'1997-02-28' AS Date), 1, 1000, N'11')
INSERT [dbo].[Books] ([bkNo], [bkTitle], [bkPrice], [bkWords], [bkFirstTime], [bkLastNumber], [bkPrtQty], [whNo]) VALUES (N'00005', N'倾城之恋', 34, 1233100, CAST(N'2005-01-01' AS Date), 23, 1000, N'02')
INSERT [dbo].[Books] ([bkNo], [bkTitle], [bkPrice], [bkWords], [bkFirstTime], [bkLastNumber], [bkPrtQty], [whNo]) VALUES (N'00006', N'情人', 34, 123, CAST(N'2005-01-01' AS Date), 1, 1000, N'02')
INSERT [dbo].[Books] ([bkNo], [bkTitle], [bkPrice], [bkWords], [bkFirstTime], [bkLastNumber], [bkPrtQty], [whNo]) VALUES (N'00007', N'挪威的森林', 34, 123, CAST(N'1992-05-30' AS Date), 1, 1523, N'31')
INSERT [dbo].[Books] ([bkNo], [bkTitle], [bkPrice], [bkWords], [bkFirstTime], [bkLastNumber], [bkPrtQty], [whNo]) VALUES (N'00008', N'失乐园', 12, 10000, CAST(N'2005-01-01' AS Date), 6, 18001, N'02')
INSERT [dbo].[Books] ([bkNo], [bkTitle], [bkPrice], [bkWords], [bkFirstTime], [bkLastNumber], [bkPrtQty], [whNo]) VALUES (N'00009', N'男人这东西', 12, 31123, CAST(N'2001-01-01' AS Date), 1, 1000, N'02')
INSERT [dbo].[Books] ([bkNo], [bkTitle], [bkPrice], [bkWords], [bkFirstTime], [bkLastNumber], [bkPrtQty], [whNo]) VALUES (N'00010', N'围城', 12, 31123, CAST(N'2005-01-01' AS Date), 3, 4023, N'02')
INSERT [dbo].[Books] ([bkNo], [bkTitle], [bkPrice], [bkWords], [bkFirstTime], [bkLastNumber], [bkPrtQty], [whNo]) VALUES (N'00011', N'查太莱夫人的情人', 12, 31123, CAST(N'1990-03-01' AS Date), 1, 6734, N'02')
INSERT [dbo].[Books] ([bkNo], [bkTitle], [bkPrice], [bkWords], [bkFirstTime], [bkLastNumber], [bkPrtQty], [whNo]) VALUES (N'13130', N'三体-黑暗森林', 34, 123, CAST(N'2005-01-01' AS Date), 1, 676, N'11')
SET IDENTITY_INSERT [dbo].[BookType] ON 

INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (194, N'09', N'00011')
INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (197, N'09', N'00007')
INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (198, N'09', N'00010')
INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (199, N'09', N'00008')
INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (201, N'03', N'00001')
INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (202, N'04', N'00001')
INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (203, N'04', N'13130')
INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (204, N'07', N'13130')
INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (205, N'99', N'13130')
INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (207, N'01', N'00003')
INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (210, N'03', N'00002')
INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (212, NULL, N'00005')
INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (213, NULL, N'00006')
INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (214, N'09', N'00009')
INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (215, N'04', N'00004')
INSERT [dbo].[BookType] ([btId], [typeNo], [bkNo]) VALUES (216, N'07', N'00004')
SET IDENTITY_INSERT [dbo].[BookType] OFF
INSERT [dbo].[Departments] ([deptNO], [deptTitle], [deptManager], [deptTelephone], [deptAddress]) VALUES (N'02', N'监管部    ', N'12345', N'13968869792  ', N'温州商学院')
INSERT [dbo].[Departments] ([deptNO], [deptTitle], [deptManager], [deptTelephone], [deptAddress]) VALUES (N'03', N'印刷部    ', N'12345', N'13968869792  ', N'温州商学院')
INSERT [dbo].[Departments] ([deptNO], [deptTitle], [deptManager], [deptTelephone], [deptAddress]) VALUES (N'04', N'财务部    ', N'12345', N'13968869792  ', N'温州商学院')
INSERT [dbo].[Departments] ([deptNO], [deptTitle], [deptManager], [deptTelephone], [deptAddress]) VALUES (N'05', N'人力部    ', N'12345', N'13968869792  ', N'温州商学院')
INSERT [dbo].[Departments] ([deptNO], [deptTitle], [deptManager], [deptTelephone], [deptAddress]) VALUES (N'06', N'技术部    ', N'12345', N'13968869792  ', N'温州商学院')
INSERT [dbo].[Employee] ([empNo], [empName], [empSex], [empBirthday], [empEntrytime], [empProvince], [empCity], [empempZip], [empAdress], [empTelephone], [empEmail], [deptNo], [empPwd], [empAuthority]) VALUES (N'00000', N'测试账号  ', 1, CAST(N'1996-10-01' AS Date), CAST(N'2018-09-28' AS Date), N'浙江省              ', N'温州市              ', N'312400', N'温州商学院', N'13968869792  ', N'zh_jl@qq.com', N'02', N'6934105ad50010b814c933314b1da6841431bc8b', N'22222222')
INSERT [dbo].[Employee] ([empNo], [empName], [empSex], [empBirthday], [empEntrytime], [empProvince], [empCity], [empempZip], [empAdress], [empTelephone], [empEmail], [deptNo], [empPwd], [empAuthority]) VALUES (N'01123', N'涂嘉庆    ', 1, CAST(N'1996-10-01' AS Date), CAST(N'2018-09-28' AS Date), N'浙江省              ', N'温州市              ', N'312400', N'温州商学院', N'13968869792  ', N'zh_jl@qq.com', N'02', N'd033e22ae348aeb5660fc2140aec35850c4da997', N'21210020')
INSERT [dbo].[Employee] ([empNo], [empName], [empSex], [empBirthday], [empEntrytime], [empProvince], [empCity], [empempZip], [empAdress], [empTelephone], [empEmail], [deptNo], [empPwd], [empAuthority]) VALUES (N'01124', N'薛定谔    ', 1, CAST(N'1996-10-01' AS Date), CAST(N'2018-09-28' AS Date), N'浙江省              ', N'温州市              ', N'312400', N'温州商学院', N'13968869792  ', N'zh_jl@qq.com', N'03', N'40bd001563085fc35165329ea1ff5c5ecbdbbeef', N'22222222')
INSERT [dbo].[Employee] ([empNo], [empName], [empSex], [empBirthday], [empEntrytime], [empProvince], [empCity], [empempZip], [empAdress], [empTelephone], [empEmail], [deptNo], [empPwd], [empAuthority]) VALUES (N'01125', N'希格斯    ', 1, CAST(N'1996-10-01' AS Date), CAST(N'2018-09-28' AS Date), N'浙江省              ', N'温州市              ', N'312400', N'温州商学院', N'13968869792  ', N'zh_jl@qq.com', N'03', N'5f0fc38bb55827c1abab8f83b03400281e2975dd', N'21012102')
INSERT [dbo].[Employee] ([empNo], [empName], [empSex], [empBirthday], [empEntrytime], [empProvince], [empCity], [empempZip], [empAdress], [empTelephone], [empEmail], [deptNo], [empPwd], [empAuthority]) VALUES (N'01126', N'竺可桢    ', 1, CAST(N'1996-06-01' AS Date), CAST(N'2018-09-28' AS Date), N'浙江省              ', N'温州市              ', N'312400', N'温州商学院', N'13968869792  ', N'zh_jl@qq.com', N'04', N'8bd7954c40c1e59a900f71ea3a266732609915b1', N'20202002')
INSERT [dbo].[Employee] ([empNo], [empName], [empSex], [empBirthday], [empEntrytime], [empProvince], [empCity], [empempZip], [empAdress], [empTelephone], [empEmail], [deptNo], [empPwd], [empAuthority]) VALUES (N'01129', N'爱因斯坦  ', 0, CAST(N'1996-10-01' AS Date), CAST(N'2018-09-28' AS Date), N'浙江省              ', N'温州市              ', N'312400', N'温州商学院', N'13968869792  ', N'zh_jl@qq.com', N'02', N'7c4a8d09ca3762af61e59520943dc26494f8941b', N'11102012')
INSERT [dbo].[Employee] ([empNo], [empName], [empSex], [empBirthday], [empEntrytime], [empProvince], [empCity], [empempZip], [empAdress], [empTelephone], [empEmail], [deptNo], [empPwd], [empAuthority]) VALUES (N'11111', N'无权限    ', 1, CAST(N'1996-10-01' AS Date), CAST(N'2018-09-28' AS Date), N'浙江省              ', N'温州市              ', N'312400', N'温州商学院', N'13968869792  ', N'zh_jl@qq.com', N'02', N'7b21848ac9af35be0ddb2d6b9fc3851934db8420', N'00000000')
INSERT [dbo].[Employee] ([empNo], [empName], [empSex], [empBirthday], [empEntrytime], [empProvince], [empCity], [empempZip], [empAdress], [empTelephone], [empEmail], [deptNo], [empPwd], [empAuthority]) VALUES (N'22222', N'不可操作  ', 1, CAST(N'1996-10-01' AS Date), CAST(N'2018-09-28' AS Date), N'浙江省              ', N'温州市              ', N'312400', N'温州商学院', N'13968869792  ', N'zh_jl@qq.com', N'02', N'1a9b9508b6003b68ddfe03a9c8cbc4bd4388339b', N'11111110')
INSERT [dbo].[Employee] ([empNo], [empName], [empSex], [empBirthday], [empEntrytime], [empProvince], [empCity], [empempZip], [empAdress], [empTelephone], [empEmail], [deptNo], [empPwd], [empAuthority]) VALUES (N'33333', N'郑健磊    ', 1, CAST(N'1996-10-01' AS Date), CAST(N'2018-09-28' AS Date), N'浙江省              ', N'温州市              ', N'312400', N'温州商学院', N'13968869792  ', N'zh_jl@qq.com', N'03', N'6b2517cbf88d895d1023d3186a38d37f3c3066e8', N'00000002')
INSERT [dbo].[Employee] ([empNo], [empName], [empSex], [empBirthday], [empEntrytime], [empProvince], [empCity], [empempZip], [empAdress], [empTelephone], [empEmail], [deptNo], [empPwd], [empAuthority]) VALUES (N'44444', N'匡芳君    ', 1, CAST(N'1996-10-01' AS Date), CAST(N'2018-09-28' AS Date), N'浙江省              ', N'温州市              ', N'312400', N'温州商学院', N'13968869792  ', N'zh_jl@qq.com', N'03', N'c984aed014aec7623a54f0591da07a85fd4b762d', N'02100202')
INSERT [dbo].[Employee] ([empNo], [empName], [empSex], [empBirthday], [empEntrytime], [empProvince], [empCity], [empempZip], [empAdress], [empTelephone], [empEmail], [deptNo], [empPwd], [empAuthority]) VALUES (N'55555', N'薛益鸽    ', 1, CAST(N'1996-10-01' AS Date), CAST(N'2018-09-28' AS Date), N'浙江省              ', N'温州市              ', N'312400', N'温州商学院', N'13968869792  ', N'zh_jl@qq.com', N'03', N'c984aed014aec7623a54f0591da07a85fd4b762d', N'02202202')
SET IDENTITY_INSERT [dbo].[InoutWH] ON 

INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (2, N'01', N'13130', N'1', CAST(N'2018-10-05' AS Date), 45688, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (3, N'01', N'13130', N'1', CAST(N'2018-10-05' AS Date), 45688, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (4, N'01', N'00003', N'1', CAST(N'2018-10-05' AS Date), 1000, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (7, N'01', N'00003', N'1', CAST(N'2018-10-05' AS Date), 800, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (8, N'01', N'00011', N'1', CAST(N'2018-10-05' AS Date), 2000, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (10, N'01', N'00007', N'1', CAST(N'2018-10-05' AS Date), 500, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (11, N'01', N'00010', N'1', CAST(N'2018-10-05' AS Date), 2000, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (12, N'01', N'00011', N'1', CAST(N'2018-10-05' AS Date), 2000, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (14, N'01', N'00011', N'2', CAST(N'2018-10-07' AS Date), 100, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (15, N'01', N'00011', N'2', CAST(N'2018-10-07' AS Date), 1000, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (16, N'01', N'00001', N'2', CAST(N'2018-10-07' AS Date), 1, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (17, N'01', N'00011', N'2', CAST(N'2018-10-07' AS Date), 100, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (18, N'01', N'00011', N'2', CAST(N'2018-10-07' AS Date), 100, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (19, N'01', N'00011', N'2', CAST(N'2018-10-07' AS Date), 1000, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (20, N'01', N'00001', N'2', CAST(N'2018-10-07' AS Date), 1, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (21, N'01', N'00001', N'1', CAST(N'2018-10-07' AS Date), 10000, N'00000', N'test')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (22, N'01', N'00011', N'1', CAST(N'2018-10-07' AS Date), 10000, N'00000', N'test')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (23, N'01', N'00008', N'1', CAST(N'2018-10-07' AS Date), 10000, N'00000', N'test')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (24, N'01', N'00011', N'2', CAST(N'2018-10-07' AS Date), 100, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (25, N'01', N'00011', N'2', CAST(N'2018-10-07' AS Date), 1000, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (26, N'01', N'00001', N'2', CAST(N'2018-10-07' AS Date), 1, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (27, N'01', N'13130', N'2', CAST(N'2018-10-07' AS Date), 1000, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (28, N'01', N'00010', N'2', CAST(N'2018-10-07' AS Date), 100, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (29, N'01', N'13130', N'2', CAST(N'2018-10-07' AS Date), 13, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (30, N'01', N'00008', N'2', CAST(N'2018-10-07' AS Date), 1000, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (31, N'01', N'00001', N'2', CAST(N'2018-10-07' AS Date), 3, N'00000', N'手动')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (32, N'01', N'00005', N'1', CAST(N'2018-10-07' AS Date), 10000, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (33, N'01', N'00008', N'1', CAST(N'2018-10-07' AS Date), 20000, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (34, N'01', N'00003', N'1', CAST(N'2018-10-07' AS Date), 999999, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (35, N'01', N'00005', N'2', CAST(N'2018-10-07' AS Date), 10000, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (36, N'01', N'00008', N'2', CAST(N'2018-10-07' AS Date), 9999, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (37, N'01', N'00011', N'2', CAST(N'2018-10-07' AS Date), 78, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (38, N'01', N'00011', N'1', CAST(N'2018-10-07' AS Date), 10000, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (39, N'01', N'00010', N'1', CAST(N'2018-10-07' AS Date), 10000, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (40, N'01', N'13130', N'1', CAST(N'2018-10-07' AS Date), 500, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (41, N'01', N'00007', N'1', CAST(N'2018-10-07' AS Date), 500, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (42, N'01', N'13130', N'2', CAST(N'2018-10-07' AS Date), 9998, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (43, N'01', N'00011', N'2', CAST(N'2018-10-07' AS Date), 8888, N'00000', N'')
INSERT [dbo].[InoutWH] ([ioId], [whNo], [bkNo], [ioType], [ioTime], [ioQuantity], [empNo], [prtRemark]) VALUES (44, N'01', N'00010', N'2', CAST(N'2018-10-07' AS Date), 7777, N'00000', N'')
SET IDENTITY_INSERT [dbo].[InoutWH] OFF
SET IDENTITY_INSERT [dbo].[OrderDetails] ON 

INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (90, N'20181007007', N'00011', 1000, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (91, N'20181007005', N'00011', 100, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (92, N'20181007005', N'00011', 1000, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (93, N'20181007005', N'00001', 1, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (101, N'20181007008', N'13130', 1000, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (102, N'20181007008', N'00010', 100, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (103, N'20181007008', N'13130', 13, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (104, N'20181007008', N'00008', 1000, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (138, N'20181007009', N'00005', 10000, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (139, N'20181007009', N'00008', 9999, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (140, N'20181007009', N'00011', 78, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (141, N'20181007012', N'00005', 10000, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (142, N'20181007012', N'00006', 99999, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (143, N'20181007012', N'00011', 78, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (144, N'20181007013', N'13130', 111, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (145, N'20181007013', N'00007', 222, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (158, N'20181007014', N'13130', 9998, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (159, N'20181007014', N'00011', 8888, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (160, N'20181007014', N'00010', 7777, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (161, N'20181007015', N'13130', 9998, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (162, N'20181007015', N'00011', 8888, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (163, N'20181007015', N'00010', 7777, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (164, N'20181007015', N'00007', 10, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (165, N'20181007015', N'00001', 10, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (166, N'20181007015', N'00002', 10, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (167, N'20181007016', N'00001', 100, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (168, N'20181007016', N'00002', 100, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (169, N'20181007016', N'00003', 100, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (170, N'20181007016', N'00005', 100, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (171, N'20181007016', N'00004', 100, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (172, N'20181007016', N'00006', 100, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (173, N'20181007016', N'00007', 100, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (174, N'20181007016', N'00008', 100, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (175, N'20181007016', N'00009', 100, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (176, N'20181007016', N'00010', 100, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (177, N'20181007016', N'00011', 100, NULL)
INSERT [dbo].[OrderDetails] ([odId], [ordNo], [bkNo], [odQuantity], [prtNumber]) VALUES (178, N'20181007016', N'13130', 100, NULL)
SET IDENTITY_INSERT [dbo].[OrderDetails] OFF
INSERT [dbo].[Orders] ([ordNo], [selNo], [empNo], [ordTime], [ordSendtime], [ordQuantity], [ordPayment], [ordDiscount], [prtRemark], [ordState]) VALUES (N'20181007005', N'00002', N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), CAST(N'2018-10-08' AS Date), 1101, 13332, 0.9, N'', 1)
INSERT [dbo].[Orders] ([ordNo], [selNo], [empNo], [ordTime], [ordSendtime], [ordQuantity], [ordPayment], [ordDiscount], [prtRemark], [ordState]) VALUES (N'20181007007', N'00001', N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), CAST(N'2018-10-08' AS Date), 1000, 12000, 0.9, N'', 0)
INSERT [dbo].[Orders] ([ordNo], [selNo], [empNo], [ordTime], [ordSendtime], [ordQuantity], [ordPayment], [ordDiscount], [prtRemark], [ordState]) VALUES (N'20181007008', N'00001', N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), CAST(N'2018-10-09' AS Date), 2113, 47642, 0.3, N'', 1)
INSERT [dbo].[Orders] ([ordNo], [selNo], [empNo], [ordTime], [ordSendtime], [ordQuantity], [ordPayment], [ordDiscount], [prtRemark], [ordState]) VALUES (N'20181007009', N'00001', N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), CAST(N'2018-10-08' AS Date), 20077, 460924, 1, N'nilk ', 1)
INSERT [dbo].[Orders] ([ordNo], [selNo], [empNo], [ordTime], [ordSendtime], [ordQuantity], [ordPayment], [ordDiscount], [prtRemark], [ordState]) VALUES (N'20181007012', N'00001', N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), CAST(N'2018-10-08' AS Date), 110077, 3740902, 1, N'nilk ', 0)
INSERT [dbo].[Orders] ([ordNo], [selNo], [empNo], [ordTime], [ordSendtime], [ordQuantity], [ordPayment], [ordDiscount], [prtRemark], [ordState]) VALUES (N'20181007013', N'00001', NULL, CAST(N'2018-10-07T00:00:00.000' AS DateTime), CAST(N'2018-10-09' AS Date), 333, 11322, 1, N'', 0)
INSERT [dbo].[Orders] ([ordNo], [selNo], [empNo], [ordTime], [ordSendtime], [ordQuantity], [ordPayment], [ordDiscount], [prtRemark], [ordState]) VALUES (N'20181007014', N'00001', NULL, CAST(N'2018-10-07T00:00:00.000' AS DateTime), CAST(N'2018-10-08' AS Date), 26663, 539912, 1, N'Juin  发挥发挥', 2)
INSERT [dbo].[Orders] ([ordNo], [selNo], [empNo], [ordTime], [ordSendtime], [ordQuantity], [ordPayment], [ordDiscount], [prtRemark], [ordState]) VALUES (N'20181007015', N'00001', NULL, CAST(N'2018-10-07T00:00:00.000' AS DateTime), CAST(N'2018-10-08' AS Date), 26693, 541912, 1, N'多买几本', 0)
INSERT [dbo].[Orders] ([ordNo], [selNo], [empNo], [ordTime], [ordSendtime], [ordQuantity], [ordPayment], [ordDiscount], [prtRemark], [ordState]) VALUES (N'20181007016', N'00001', NULL, CAST(N'2018-10-07T00:00:00.000' AS DateTime), CAST(N'2018-10-09' AS Date), 1200, 58400, 1, N'所有书都买一本', 1)
SET IDENTITY_INSERT [dbo].[OutOfStock] ON 

INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (1, N'13130', 877, N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), N'订单出库缺货', 1)
INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (2, N'00011', 1100, N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), N'订单出库缺货', 1)
INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (3, N'00011', 1100, N'00000', CAST(N'2018-10-09T00:00:00.000' AS DateTime), N'订单出库缺货', 0)
INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (4, N'00008', 900, N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), N'订单出库缺货', 1)
INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (5, N'00005', 9877, N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), N'订单出库缺货', 1)
INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (6, N'00008', 1899, N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), N'订单出库缺货', 1)
INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (7, N'00003', 99077, N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), N'订单出库缺货', 1)
INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (8, N'00006', 99876, N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), N'订单出库缺货', 1)
INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (9, N'00011', 3166, N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), N'订单出库缺货', 1)
INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (10, N'00010', 5877, N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), N'订单出库缺货', 1)
INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (11, N'13130', 9222, N'00000', CAST(N'2018-10-09T00:00:00.000' AS DateTime), N'订单出库缺货', 1)
INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (12, N'00011', 2054, N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), N'订单出库缺货', 1)
INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (13, N'00010', 3654, N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), N'订单出库缺货', 1)
INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (14, N'13130', 9322, N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), N'订单出库缺货', 1)
INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (15, N'00011', 2154, N'00000', CAST(N'2018-10-07T00:00:00.000' AS DateTime), N'订单出库缺货', 1)
INSERT [dbo].[OutOfStock] ([oosId], [bkNo], [oosQuantity], [empNo], [oosTime], [oosUse], [oosState]) VALUES (16, N'00010', 3754, N'00000', CAST(N'2018-10-09T00:00:00.000' AS DateTime), N'订单出库缺货', 0)
SET IDENTITY_INSERT [dbo].[OutOfStock] OFF
SET IDENTITY_INSERT [dbo].[PubPrint] ON 

INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (2, N'00003', CAST(N'2020-01-01' AS Date), 800, 1, N'00000', N'', 1)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (3, N'00005', CAST(N'2020-01-01' AS Date), 1000, 23, N'00000', N'', 1)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (4, N'00007', CAST(N'2020-01-01' AS Date), 500, 1, N'00000', N'', 1)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (5, N'00007', CAST(N'2020-01-01' AS Date), 1000, 1, N'00000', N'', 1)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (6, N'00011', CAST(N'2020-01-01' AS Date), 2000, 1, N'00000', N'', 1)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (7, N'00003', CAST(N'2020-01-01' AS Date), 1000, 1, NULL, N'', 0)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (9, N'00007', CAST(N'2020-01-01' AS Date), 500, 1, N'00000', N'', 1)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (10, N'13130', CAST(N'2018-07-01' AS Date), 45688, 1, N'00000', N'', 1)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (11, N'13130', CAST(N'2018-10-09' AS Date), 2000, 1, N'00000', N'', 0)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (12, N'00011', CAST(N'2018-10-09' AS Date), 2000, 1, N'00000', N'', 1)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (13, N'00010', CAST(N'2018-10-09' AS Date), 2000, 1, N'00000', N'', 0)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (14, N'13130', CAST(N'2018-11-01' AS Date), 500, 1, N'00000', N'', 1)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (15, N'13130', CAST(N'2020-01-01' AS Date), 500, 1, NULL, N'', 0)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (16, N'00010', CAST(N'2018-10-09' AS Date), 2000, 3, N'00000', N'', 1)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (17, N'00010', CAST(N'2018-10-09' AS Date), 2000, 3, N'00000', N'', 0)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (18, N'00005', CAST(N'2018-10-07' AS Date), 10000, 1, N'00000', N'', 1)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (19, N'00008', CAST(N'2018-10-07' AS Date), 20000, 1, N'00000', N'', 1)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (20, N'00003', CAST(N'2018-10-07' AS Date), 999999, 1, N'00000', N'', 1)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (21, N'00011', CAST(N'2018-10-07' AS Date), 10000, 1, N'00000', N'', 1)
INSERT [dbo].[PubPrint] ([prtID], [bkNo], [prtTime], [prtQuantity], [prtNumber], [empNo], [prtRemark], [prtState]) VALUES (22, N'00010', CAST(N'2018-10-07' AS Date), 10000, 1, N'00000', N'', 1)
SET IDENTITY_INSERT [dbo].[PubPrint] OFF
INSERT [dbo].[Sellers] ([selNo], [selTitle], [selName], [selTelephone], [selProvince], [selCity], [selZip], [selAddress], [selEmail], [selDeliAddress], [selRemark], [selPwd]) VALUES (N'00001', N'温州商学院', N'匡芳君    ', N'1234646346   ', N'浙江省              ', N'温州市              ', N'312467', N'温州商学院', N'zh_jl@qq.com', N'F区', N'', N'356a192b7913b04c54574d18c28d46e6395428ab')
INSERT [dbo].[Sellers] ([selNo], [selTitle], [selName], [selTelephone], [selProvince], [selCity], [selZip], [selAddress], [selEmail], [selDeliAddress], [selRemark], [selPwd]) VALUES (N'00002', N'温州大学  ', N'郑健磊    ', N'13976677878  ', N'吉林省              ', N'温州市              ', N'000000', N'温州商学院', N'zh_jl@qq.com', N'F区', N'www', N'c984aed014aec7623a54f0591da07a85fd4b762d')
INSERT [dbo].[Sellers] ([selNo], [selTitle], [selName], [selTelephone], [selProvince], [selCity], [selZip], [selAddress], [selEmail], [selDeliAddress], [selRemark], [selPwd]) VALUES (N'00003', N'北京大学  ', N'薛益鸽    ', N'12354445566  ', N'北京市              ', N'温州市              ', N'312467', N'温州商学院', N'zh_jl@qq.com', N'F区', N'www', N'c984aed014aec7623a54f0591da07a85fd4b762d')
INSERT [dbo].[Sellers] ([selNo], [selTitle], [selName], [selTelephone], [selProvince], [selCity], [selZip], [selAddress], [selEmail], [selDeliAddress], [selRemark], [selPwd]) VALUES (N'00004', N'家里蹲大学', N'涂嘉庆    ', N'13355556677  ', N'山西省              ', N'温州市              ', N'312467', N'温州商学院', N'zh_jl@qq.com', N'F区', N'www', N'c984aed014aec7623a54f0591da07a85fd4b762d')
INSERT [dbo].[Sellers] ([selNo], [selTitle], [selName], [selTelephone], [selProvince], [selCity], [selZip], [selAddress], [selEmail], [selDeliAddress], [selRemark], [selPwd]) VALUES (N'00005', N'坐地铁大学', N'肯德基    ', N'13144554466  ', N'山西省              ', N'温州市              ', N'312467', N'温州商学院', N'zh_jl@qq.com', N'F区', N'www', N'c984aed014aec7623a54f0591da07a85fd4b762d')
INSERT [dbo].[Sellers] ([selNo], [selTitle], [selName], [selTelephone], [selProvince], [selCity], [selZip], [selAddress], [selEmail], [selDeliAddress], [selRemark], [selPwd]) VALUES (N'00006', N'清华大学  ', N'薛益鸽    ', N'12354445566  ', N'北京市              ', N'温州市              ', N'312467', N'温州商学院', N'zh_jl@qq.com', N'F区', N'www', N'c984aed014aec7623a54f0591da07a85fd4b762d')
INSERT [dbo].[types] ([typeNo], [typeTitle], [typeRemark]) VALUES (N'01', N'教科书    ', N'21')
INSERT [dbo].[types] ([typeNo], [typeTitle], [typeRemark]) VALUES (N'03', N'中国历史  ', N'')
INSERT [dbo].[types] ([typeNo], [typeTitle], [typeRemark]) VALUES (N'04', N'儿童读物  ', N'')
INSERT [dbo].[types] ([typeNo], [typeTitle], [typeRemark]) VALUES (N'05', N'世界历史  ', N'')
INSERT [dbo].[types] ([typeNo], [typeTitle], [typeRemark]) VALUES (N'06', N'地理      ', N'')
INSERT [dbo].[types] ([typeNo], [typeTitle], [typeRemark]) VALUES (N'07', N'天文      ', N'')
INSERT [dbo].[types] ([typeNo], [typeTitle], [typeRemark]) VALUES (N'08', N'数学      ', N'')
INSERT [dbo].[types] ([typeNo], [typeTitle], [typeRemark]) VALUES (N'09', N'现在文学  ', N'')
INSERT [dbo].[types] ([typeNo], [typeTitle], [typeRemark]) VALUES (N'99', N'测试      ', N'')
INSERT [dbo].[Warehouse] ([whNo], [empNo], [whTelephone], [whAddress], [whArea], [whRemark]) VALUES (N'01', N'00000', N'12111111111  ', N'wz                                                ', 165, N'')
INSERT [dbo].[Warehouse] ([whNo], [empNo], [whTelephone], [whAddress], [whArea], [whRemark]) VALUES (N'02', N'00000', N'12111111111  ', N'wz                                                ', 165, N'')
INSERT [dbo].[Warehouse] ([whNo], [empNo], [whTelephone], [whAddress], [whArea], [whRemark]) VALUES (N'11', N'01123', N'12111111111  ', N'wz                                                ', 165, N'')
INSERT [dbo].[Warehouse] ([whNo], [empNo], [whTelephone], [whAddress], [whArea], [whRemark]) VALUES (N'31', N'00000', N'12111111111  ', N'wz                                                ', 165, N'')
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Accounts__215DBAA5D438E6C3]    Script Date: 2018/10/9 下午7:32:37 ******/
ALTER TABLE [dbo].[Accounts] ADD UNIQUE NONCLUSTERED 
(
	[ordNo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Accounts] ADD  DEFAULT ((0)) FOR [ordPayment]
GO
ALTER TABLE [dbo].[Accounts] ADD  DEFAULT ((0)) FOR [accPayment]
GO
ALTER TABLE [dbo].[Accounts] ADD  DEFAULT ((1)) FOR [ordTap]
GO
ALTER TABLE [dbo].[Authored] ADD  DEFAULT ((1)) FOR [auOrder]
GO
ALTER TABLE [dbo].[Authors] ADD  DEFAULT ((1)) FOR [auSex]
GO
ALTER TABLE [dbo].[Books] ADD  DEFAULT ((1)) FOR [bkLastNumber]
GO
ALTER TABLE [dbo].[Books] ADD  DEFAULT ((0)) FOR [bkPrtQty]
GO
ALTER TABLE [dbo].[Contracts] ADD  DEFAULT ((2000)) FOR [conNumber]
GO
ALTER TABLE [dbo].[Contracts] ADD  DEFAULT ((8)) FOR [conRoyalty]
GO
ALTER TABLE [dbo].[Contracts] ADD  DEFAULT ((1)) FOR [conPay]
GO
ALTER TABLE [dbo].[InoutWH] ADD  DEFAULT (getdate()) FOR [ioTime]
GO
ALTER TABLE [dbo].[OrderDetails] ADD  DEFAULT ((0)) FOR [odQuantity]
GO
ALTER TABLE [dbo].[Orders] ADD  DEFAULT (getdate()) FOR [ordTime]
GO
ALTER TABLE [dbo].[Orders] ADD  DEFAULT ((0)) FOR [ordQuantity]
GO
ALTER TABLE [dbo].[Orders] ADD  DEFAULT ((0)) FOR [ordPayment]
GO
ALTER TABLE [dbo].[Orders] ADD  DEFAULT ((0.9)) FOR [ordDiscount]
GO
ALTER TABLE [dbo].[OutOfStock] ADD  DEFAULT ((0)) FOR [oosQuantity]
GO
ALTER TABLE [dbo].[PubPrint] ADD  DEFAULT (getdate()) FOR [prtTime]
GO
ALTER TABLE [dbo].[PubPrint] ADD  DEFAULT ((2000)) FOR [prtQuantity]
GO
ALTER TABLE [dbo].[PubPrint] ADD  DEFAULT ((1)) FOR [prtNumber]
GO
ALTER TABLE [dbo].[Accounts]  WITH CHECK ADD FOREIGN KEY([selNo])
REFERENCES [dbo].[Sellers] ([selNo])
GO
ALTER TABLE [dbo].[Authored]  WITH CHECK ADD FOREIGN KEY([auNo])
REFERENCES [dbo].[Authors] ([auNo])
GO
ALTER TABLE [dbo].[Authored]  WITH CHECK ADD FOREIGN KEY([bkNo])
REFERENCES [dbo].[Books] ([bkNo])
GO
ALTER TABLE [dbo].[Books]  WITH CHECK ADD FOREIGN KEY([whNo])
REFERENCES [dbo].[Warehouse] ([whNo])
GO
ALTER TABLE [dbo].[BookType]  WITH CHECK ADD FOREIGN KEY([bkNo])
REFERENCES [dbo].[Books] ([bkNo])
GO
ALTER TABLE [dbo].[BookType]  WITH CHECK ADD FOREIGN KEY([typeNo])
REFERENCES [dbo].[types] ([typeNo])
GO
ALTER TABLE [dbo].[Contracts]  WITH CHECK ADD FOREIGN KEY([auNo])
REFERENCES [dbo].[Authors] ([auNo])
GO
ALTER TABLE [dbo].[Contracts]  WITH CHECK ADD FOREIGN KEY([bkNo])
REFERENCES [dbo].[Books] ([bkNo])
GO
ALTER TABLE [dbo].[Contracts]  WITH CHECK ADD FOREIGN KEY([empNo])
REFERENCES [dbo].[Employee] ([empNo])
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD FOREIGN KEY([deptNo])
REFERENCES [dbo].[Departments] ([deptNO])
GO
ALTER TABLE [dbo].[InoutWH]  WITH CHECK ADD FOREIGN KEY([bkNo])
REFERENCES [dbo].[Books] ([bkNo])
GO
ALTER TABLE [dbo].[InoutWH]  WITH CHECK ADD FOREIGN KEY([empNo])
REFERENCES [dbo].[Employee] ([empNo])
GO
ALTER TABLE [dbo].[InoutWH]  WITH CHECK ADD FOREIGN KEY([whNo])
REFERENCES [dbo].[Warehouse] ([whNo])
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD FOREIGN KEY([bkNo])
REFERENCES [dbo].[Books] ([bkNo])
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD FOREIGN KEY([empNo])
REFERENCES [dbo].[Employee] ([empNo])
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD FOREIGN KEY([selNo])
REFERENCES [dbo].[Sellers] ([selNo])
GO
ALTER TABLE [dbo].[OutOfStock]  WITH CHECK ADD FOREIGN KEY([empNo])
REFERENCES [dbo].[Employee] ([empNo])
GO
ALTER TABLE [dbo].[OutOfStock]  WITH CHECK ADD FOREIGN KEY([bkNo])
REFERENCES [dbo].[Books] ([bkNo])
GO
ALTER TABLE [dbo].[PubPrint]  WITH CHECK ADD FOREIGN KEY([bkNo])
REFERENCES [dbo].[Books] ([bkNo])
GO
ALTER TABLE [dbo].[PubPrint]  WITH CHECK ADD FOREIGN KEY([empNo])
REFERENCES [dbo].[Employee] ([empNo])
GO
ALTER TABLE [dbo].[Warehouse]  WITH CHECK ADD FOREIGN KEY([empNo])
REFERENCES [dbo].[Employee] ([empNo])
GO
ALTER TABLE [dbo].[Accounts]  WITH CHECK ADD CHECK  (([ordTap]='2' OR [ordTap]='1' OR [ordTap]='0'))
GO
ALTER TABLE [dbo].[Contracts]  WITH CHECK ADD CHECK  (([conNumber]>=(0) AND [conNumber]<=(10000)))
GO
ALTER TABLE [dbo].[Contracts]  WITH CHECK ADD CHECK  (([conRoyalty]>=(0) AND [conRoyalty]<=(50)))
GO
ALTER TABLE [dbo].[InoutWH]  WITH CHECK ADD CHECK  (([ioType]>=(1) AND [ioType]<=(4)))
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD CHECK  (([ordSendtime]>[ordTime]))
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [CK__Orders__ordDisco__70DDC3D8] CHECK  (([ordDiscount]>(0) AND [ordDiscount]<=(1)))
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [CK__Orders__ordDisco__70DDC3D8]
GO
ALTER TABLE [dbo].[PubPrint]  WITH CHECK ADD CHECK  (([prtNumber]>(0)))
GO
/****** Object:  StoredProcedure [dbo].[proc_genereteOradeNo]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

--④生成订货单编号：订货单编号由存储过程自动生成，前8位为当天的日期，后3位为当天的单子序号，
--比如2018年12月1日的第一张订货单编号为20181201001，以此类推。

CREATE  PROC [dbo].[proc_genereteOradeNo]
(@orderNo varchar(11) OUTPUT)
AS
BEGIN
	
	DECLARE  @maxOrderNo      char(11)            --存放订单表中已有的最大订单号
			,@dateToChar char(8)                  --最大订单号前8位日期字符串
			,@maxNo		 char(3)                  --最大订单号中后三位编号
			,@fourNo	 char(4)                  --为了方便处理前面的0
	SELECT  @maxOrderNo = max(ordNo) from orders
	SELECT  @dateToChar = CONVERT(char(8),@maxOrderNo),@maxNo = RIGHT(@maxOrderNo,3)
	--print 'maxOrderNo:'+@maxorderNo +'maxNo:' + @maxNo
	IF @dateToChar = CONVERT(char(8),GETDATE(),112)
	BEGIN
		--SELECT @maxNo = CAST(CONVERT(smallint,@maxNo)+1 AS int)
		SELECT @fourNo = '1'+@maxNo
		SELECT @maxNo = RIGHT(CAST(CONVERT(smallint,@fourNo)+1 AS CHAR(4)),3)
	END
	ELSE
		SELECT @dateToChar =  CONVERT(char(8),GETDATE(),112),@maxNo = '001'
	SELECT @orderNo = @dateToChar + @maxNo
	RETURN @orderNo
END
GO
/****** Object:  StoredProcedure [dbo].[proc_ProcessOrder]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

--⑤ 指定日期的订购单处理：默认情况下，对当天的所有订购单进行处理，当所购的图书库存充足时，直接生成出库单，
-----以便仓库出货，并修改图书的当前量；当库存不足时，将自动生成一条缺货记录。

CREATE PROC [dbo].[proc_ProcessOrder]
(@date datetime = NULL)                    --设置默认值NULL是为了传进默认值当天日期
AS
BEGIN
	DECLARE  @orderNo  char(11)
			,@bkNo	   char(5)
			,@orderQty int
			,@prtQty   int
			,@whno	   char(2)
			,@empNo    char(5)
	DECLARE c1 CURSOR FOR SELECT od.ordNo,od.bkNo, od.odQuantity,bkPrtQty,whNo,empNo
			FROM orderDetails od JOIN books ON od.bkNo = books.bkNo JOIN orders ON od.ordNo = orders.ordNo
			WHERE orders.ordTime = isnull(@date,getdate());       --isnull函数如果@date的值不为null，结果返回@date;
			                                                      --如果@date的值为null，结果返回getdate()的值。 
	OPEN c1;
	FETCH C1 INTO @orderNo,@bkNo,@orderQty,@prtQty,@whNo,@empNo;
	WHILE(@@FETCH_STATUS = 0)
	BEGIN 
		IF @orderQty <= @prtQty
		BEGIN
			INSERT INTO InoutWH(whNo,bkNo,ioType,ioTime,ioQuantity,empNo)  
			VALUES(@whNo,@bkNo,'2',getdate(),@orderQty,@empNo);
			UPDATE books 
			SET bkPrtQty = bkPrtQty - @orderQty 
			WHERE bkNo = @bkNo;
		END
		INSERT INTO OutOfStock(bkNo,oosQuantity,empNo,oosTime)
		VALUES(@bkNo ,@orderQty ,@empNo ,getdate())
	END
	CLOSE c1;
	DEALLOCATE c1;
END
GO
/****** Object:  StoredProcedure [dbo].[proc_ReturncContracts]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

--② 根据时间段，查看该时间段内已签订的图书合同的情况，包括合同编号、图书编号、图书名、第一作者姓名、
-----联系电话、联系地址、邮箱、首印数量、交稿时间、版税、付款方式、签订时间

CREATE PROCEDURE [dbo].[proc_ReturncContracts]
(@startTime DATE, @endTime DATE)
AS
BEGIN
    SELECT con.conNo, bk.bkNo, bkTitle, au.auName, auTelephone, auAddress, auEmail, conNumber, conRoyalty, '付款方式'=
			CASE conPay WHEN 1 THEN '销售完付全款'
						WHEN 2 THEN '印刷后即付全款'
						WHEN 3 THEN '销售和印刷后各付一半版税'
			END, 
		conTime
    FROM Contracts con join  books bk on con.bkNo=bk.bkNo 
		JOIN Authored aud on bk.bkNo = aud.bkNo JOIN Authors au ON aud.auNO = au.auNo
    WHERE  auOrder = 1  AND conTime BETWEEN @startTime AND @endTime;
END
GO
/****** Object:  StoredProcedure [dbo].[proc_ReturnPrtQty]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--存储过程
--① 按图书编号，查看图书的库存量：proc_ReturnPrtQty
CREATE PROCEDURE [dbo].[proc_ReturnPrtQty]
(@no CHAR(5))
AS

    SELECT bkPrtQty
    FROM books
    WHERE bkNo=@no
GO
/****** Object:  StoredProcedure [dbo].[proc_TitleQuantity]    Script Date: 2018/10/9 下午7:32:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

--③ 根据图书名，查看该图书的销售情况，如有多个版次，将每一版次的销售量都列出
CREATE PROC [dbo].[proc_TitleQuantity]
(@title varchar(30))
AS
BEGIN
	SELECT bkTitle,prtNumber,sum(odQuantity)
	FROM books JOIN orderDetails od ON books.bkNo = od.bkNo
	WHERE bkTitle = @title 
	GROUP BY bkTitle,prtNumber
	
END
GO
USE [master]
GO
ALTER DATABASE [Publisher] SET  READ_WRITE 
GO
