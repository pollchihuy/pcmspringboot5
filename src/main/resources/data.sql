SET IDENTITY_INSERT [projectz].[MstGroupMenu] ON
;
INSERT INTO [projectz].[MstGroupMenu] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [NamaGroupMenu]) VALUES (N'1', N'2025-05-21 20:45:57.000000', N'1', NULL, NULL, N'User Management')
;

INSERT INTO [projectz].[MstGroupMenu] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [NamaGroupMenu]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'2', NULL, NULL, N'Artikel')
;
INSERT INTO [projectz].[MstGroupMenu] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [NamaGroupMenu]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'3', NULL, NULL, N'Buat Delete')
;

SET IDENTITY_INSERT [projectz].[MstGroupMenu] OFF
;

SET IDENTITY_INSERT [projectz].[MstMenu] ON
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [NamaMenu], [Path]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'1', N'1', NULL, NULL, N'Group-Menu', N'/group-menu')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [NamaMenu], [Path]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'2', N'1', NULL, NULL, N'Menu', N'/menu')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [NamaMenu], [Path]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'3', N'1', NULL, NULL, N'Akses', N'/akses')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [NamaMenu], [Path]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'4', N'1', NULL, NULL, N'User', N'/user')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [NamaMenu], [Path]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'5', N'2', NULL, NULL, N'Artikel-1', N'/artikel-1')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [NamaMenu], [Path]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'6', N'2', NULL, NULL, N'Artikel-2', N'/artikel-2')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [NamaMenu], [Path]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'7', N'2', NULL, NULL, N'Artikel-3', N'/artikel-3')
;

SET IDENTITY_INSERT [projectz].[MstMenu] OFF
;

SET IDENTITY_INSERT [projectz].[MstAkses] ON
;

INSERT INTO [projectz].[MstAkses] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [NamaAkses]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'1', NULL, NULL, N'Admin')
;

INSERT INTO [projectz].[MstAkses] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [NamaAkses]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'2', NULL, NULL, N'Member')
;

INSERT INTO [projectz].[MstAkses] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [NamaAkses]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'3', NULL, NULL, N'Member Test')
;

SET IDENTITY_INSERT [projectz].[MstAkses] OFF;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'1')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'2')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'3')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'4')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'5')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'6')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'2', N'5')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'2', N'6')
;

SET IDENTITY_INSERT [projectz].[MstUser] ON
;

INSERT INTO [projectz].[MstUser] ([IsRegistered], [TanggalLahir], [CreatedBy], [CreatedDate], [ID], [IDAkses], [ModifiedBy], [ModifiedDate], [Username], [NoHp], [OTP], [Password], [TokenEstafet], [NamaLengkap], [Email], [LinkImage], [PathImage]) VALUES (N'1', N'1995-12-12', N'1', N'2025-05-21 21:03:38.464776', N'1', N'1', N'1', N'2025-05-21 21:03:46.874860', N'paul.123', N'081213141321', N'888890', N'$2a$11$WGf7TWx9qZ74TGXdolGFROkuZJqrtil/8ryVy5cLXkfHfZvEbrN6.', NULL, N'Paul Malau', N'poll.chihuy@gmail.com', NULL, NULL)
;

INSERT INTO [projectz].[MstUser] ([IsRegistered], [TanggalLahir], [CreatedBy], [CreatedDate], [ID], [IDAkses], [ModifiedBy], [ModifiedDate], [Username], [NoHp], [OTP], [Password], [TokenEstafet], [NamaLengkap], [Email], [LinkImage], [PathImage]) VALUES (N'1', N'2001-02-28', N'1', N'2025-05-21 21:03:38.464776', N'2', N'2', N'1', N'2025-05-21 21:03:46.874860', N'kurtcobaan.123', N'08121114132', N'$13$11$WGf7TWx9qZ74TGXdolGFROkuZJqrtil/8ryVy5cLXkfHfZvEN666', N'$13$11$WGf7TWx9qZ74TGXdolGFROkuZJqrtil/8ryVy5cLXkfHfZvEbrN6.', NULL, N'Kurt Cobaan', N'kurt.cobaan@gmail.com', NULL, NULL)
;

SET IDENTITY_INSERT [projectz].[MstUser] OFF
;