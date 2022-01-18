# API-Rest ONG
Gerenciador de matrícula - Projeto final do programa de bolsas da Compass.Uol

## Os endpoints para essa API serão os seguintes
### /api/v1/students
- POST - /api/v1/students
- GET - /api/v1/students (Filtro por nome)
- GET - /api/v1/students/{id}
- PUT - /api/v1/students/{id}
- DELETE - /api/v1/students/{id}
- GET - /api/v1/students/{id}/occurrences (Retorna todas as ocorrências vinculadas ao aluno)
- GET - /api/v1/students/occurrences (Retorna os alunos e ocorrências do responsável logado no sistema)

### /api/v1/responsible
- POST - /api/v1/responsible
- GET - /api/v1/responsible (Filtro por nome)
- GET - /api/v1/responsible/{id}
- PUT - /api/v1/responsible/{id}
- DELETE - /api/v1/responsible/{id}
- POST - /api/v1/responsible/{id}/students/{id} (Vincula um responsável a um estudante)
- POST - /api/v1/responsible/{id}/users/{id} (Vincula um responsável a um usuário)

### /api/v1/occurrences
- POST - /api/v1/occurrences
- GET - /api/v1/occurrences (Filtro por data)
- GET - /api/v1/occurrences/{id}
- PUT - /api/v1/occurrences/{id}
- DELETE - /api/v1/responsible/{id}
- POST - /api/v1/occurrences/{id}/students/{id} (Vincula uma ocorrência a um estudante)

### /api/v1/educators
- POST - /api/v1/educators
- GET - /api/v1/educators (Filtro por nome)
- GET - /api/v1/educators/{id}
- PUT - /api/v1/educators/{id}
- DELETE - /api/v1/educators/{id}

### /api/v1/courses
- POST - /api/v1/courses
- GET - /api/v1/courses (Filtro por nome)
- GET - /api/v1/courses/{id}
- PUT - /api/v1/courses/{id}
- DELETE - /api/v1/courses/{id}
- POST - /api/v1/courses/{id}/educators/{id} (Vincula um curso a um educador)
- DELETE - /api/v1/courses/{id}/educators/{id} (Desvincula um curso de um educador)

### /api/v1/classrooms
- POST - /api/v1/classrooms
- GET - /api/v1/classrooms (Filtro por data)
- GET - /api/v1/classrooms/{id}
- PUT - /api/v1/classrooms/{id}
- DELETE - /api/v1/classrooms/{id}
- POST - /api/v1/classrooms/{id}/courses/{id} (Vincula um curso a uma classe)
- DELETE - /api/v1/classrooms/{id}/courses/{id} (Desvincula um curso de uma classe)
- POST - /api/v1/classrooms/{id}/students/{id} (Vincula um estudante a uma classe)
- DELETE - /api/v1/classrooms/{id}/students/{id} (Desvincula um estudante de uma classe)

### /api/v1/coordinators
- POST - /api/v1/coordinators
- GET - /api/v1/coordinators
- GET - /api/v1/coordinators/{id}
- PUT - /api/v1/coordinators/{id}
- DELETE - /api/v1/coordinators/{id}

### /api/v1/users
- POST - /api/v1/users
- PUT - /api/v1/users/{id}
- DELETE - /api/v1/users/{id}

