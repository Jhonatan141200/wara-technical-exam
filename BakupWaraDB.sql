USE [WaraDB]
GO
/****** Object:  User [IIS APPPOOL\apiwaraweb]    Script Date: 7/11/2023 17:52:09 ******/
CREATE USER [IIS APPPOOL\apiwaraweb] FOR LOGIN [IIS APPPOOL\apiwaraweb] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [IIS APPPOOL\apiwaraweb]
GO
ALTER ROLE [db_datareader] ADD MEMBER [IIS APPPOOL\apiwaraweb]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [IIS APPPOOL\apiwaraweb]
GO
/****** Object:  Table [dbo].[__EFMigrationsHistory]    Script Date: 7/11/2023 17:52:09 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[__EFMigrationsHistory](
	[MigrationId] [nvarchar](150) NOT NULL,
	[ProductVersion] [nvarchar](32) NOT NULL,
 CONSTRAINT [PK___EFMigrationsHistory] PRIMARY KEY CLUSTERED 
(
	[MigrationId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Trabajador]    Script Date: 7/11/2023 17:52:09 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Trabajador](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Nombre] [nvarchar](max) NOT NULL,
	[Apellido] [nvarchar](max) NOT NULL,
	[Dni] [nvarchar](max) NOT NULL,
	[Edad] [int] NOT NULL,
 CONSTRAINT [PK_Trabajador] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Usuario]    Script Date: 7/11/2023 17:52:09 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuario](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Nombre] [nvarchar](max) NOT NULL,
	[Apellido] [nvarchar](max) NOT NULL,
	[User] [nvarchar](max) NOT NULL,
	[Password] [nvarchar](max) NOT NULL,
	[Role] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_Usuario] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  StoredProcedure [dbo].[EliminarUsuarioPorID]    Script Date: 7/11/2023 17:52:09 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

--STORES PROCEDURES


--INSERT INTO Usuario VALUES('Jhonatan', 'Vasquez Reyna', 'jhona141200@gmail.com', 'Wara$.', 'Admin');



--CREATE PROCEDURE VerificarLogin
--    @p_email NVARCHAR(255),
--    @p_password NVARCHAR(255)
--AS
--BEGIN
--    DECLARE @user_id INT;

--     --Buscar un usuario con el correo electrónico proporcionado
--    SELECT @user_id = [Id] FROM Usuario WHERE [User] = @p_email;

--     --Comprobar si se encontró un usuario con el correo electrónico proporcionado
--    IF @user_id IS NOT NULL
--    BEGIN
--         --Verificar si la contraseña coincide
--        IF (SELECT [Password] FROM Usuario WHERE [Id] = @user_id) = @p_password
--        BEGIN
--             --Inicio de sesión exitoso
--            SELECT 'Inicio de sesión exitoso' AS resultado;
--        END
--        ELSE
--        BEGIN
--             --Contraseña incorrecta
--            SELECT 'Contraseña incorrecta' AS resultado;
--        END
--    END
--    ELSE
--    BEGIN
--         --Usuario no encontrado
--        SELECT 'Usuario no encontrado' AS resultado;
--    END
--END;

--EXEC VerificarLogin 'Jhona141200@gmail.com', 'Wara$.';





--CREATE PROCEDURE GetUsuarioPorCredenciales
--    @Username NVARCHAR(255),
--    @Password NVARCHAR(255)
--AS
--BEGIN
--    SELECT *
--    FROM Usuario
--    WHERE [User] = @Username AND [Password] = @Password;
--END;

--EXEC GetUsuarioPorCredenciales 'Jhona141200@gmail.com', 'Wara$.';




CREATE PROCEDURE [dbo].[EliminarUsuarioPorID]
@UserID INT
AS
BEGIN
    -- Verificar si el usuario existe
    IF EXISTS (SELECT 1 FROM Trabajador WHERE [Id] = @UserID)
    BEGIN
        -- Si el usuario existe, eliminarlo
        DELETE FROM Trabajador WHERE [Id] = @UserID
        SELECT 'Usuario eliminado con éxito' AS resultado
    END
    ELSE
    BEGIN
        -- Si el usuario no existe, mostrar un mensaje de que no existe
        SELECT 'No existe un usuario con el ID proporcionado' AS resultado
    END
END
GO
/****** Object:  StoredProcedure [dbo].[GetUsuarioPorCredenciales]    Script Date: 7/11/2023 17:52:09 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetUsuarioPorCredenciales]
    @Username NVARCHAR(255),
    @Password NVARCHAR(255)
AS
BEGIN
    SELECT *
    FROM Usuario
    WHERE [User] = @Username AND [Password] = @Password;
END;
GO
/****** Object:  StoredProcedure [dbo].[VerificarLogin]    Script Date: 7/11/2023 17:52:09 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[VerificarLogin]
    @p_email NVARCHAR(255),
    @p_password NVARCHAR(255)
AS
BEGIN
    DECLARE @user_id INT;

     --Buscar un usuario con el correo electrónico proporcionado
    SELECT @user_id = [Id] FROM Usuario WHERE [User] = @p_email;

     --Comprobar si se encontró un usuario con el correo electrónico proporcionado
    IF @user_id IS NOT NULL
    BEGIN
         --Verificar si la contraseña coincide
        IF (SELECT [Password] FROM Usuario WHERE [Id] = @user_id) = @p_password
        BEGIN
             --Inicio de sesión exitoso
            SELECT 'Inicio de sesión exitoso' AS resultado;
        END
        ELSE
        BEGIN
             --Contraseña incorrecta
            SELECT 'Contraseña incorrecta' AS resultado;
        END
    END
    ELSE
    BEGIN
         --Usuario no encontrado
        SELECT 'Usuario no encontrado' AS resultado;
    END
END;
GO
