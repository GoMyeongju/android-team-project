#### android_team_project

# Team Diary

## 프로젝트 목표
> 본 프로젝트는 팀 프로젝트를 진행하는 사람들에게 더욱더 효율적으로 업무를 진행할 수 있도록 기존의 툴의 문제점을 보완하여 쉽고 간단한, 필요한 기능만 있는 툴을 제공하는 것을 목표로 한다. 

---
## 사용한 주요 기술
- **Firebase Authentication**
  - onCreate 내에서 FirebaseAuth 객체의 공유 인스턴스 가지고 옴
  - 사용자의 현재 로그인 상태 확인을 위해 getCurrnetUsr.getUID() 사용
  - 회원가입을 위해 createUserWithEmailAndPassword() 함수 사용
  - 회원가입을 완료한 사용자의 로그인을 위해 signWithEmailAndPassword() 함수 사용
    
- **Firebase Database**
  - getInstance().getReference() 를 사용하여 쓸 위치를 지정해 Java 객체를 포함하여 다양한 데이터 유형을 데이터베이스에 저장
  - put()을 통해 저장소 부모 노드를 생성
  - ValueUpdateListaner를 사용하여 앱 데이터를 실시간으로 업데이트 (리스너는 DataSnapshot 이벤트 시 데이터베이스의 지정된 위치에 있는 데이터를 포함하는지를 수신) 
  - updateChildren()을 통하여 다른 자식 노드를 덮어쓰지 않고 특정 필드를 업데이트
  - removeValue()를 사용하여 데이터를 삭제
  - orderByChild() / orderByKey() / orderByValue()를 사용하여 데이터를 정렬
  - equaltTo()를 사용하여 원하는 데이터만 뽑아와 화면에 출력
    
- **Friebase Storage**
  - getInstance().getReference() 를 사용하여 인스턴스를 생성
  - putBytes(), putFile(), putStream() 등의 함수 중 우리는 로컬 파일에서 이미지를 업로드 할 필요가 있었기 때문에 putFile() 방식을 사용하여 파일을 업로드
  - 업로드 된 파일을 다운로드 받기 위해 getDownloadUrl() 메소드를 호출하여 사용


--- 
## 프로젝트 전체 구조도
> ![project_structure](https://user-images.githubusercontent.com/51225037/105517312-400c1a80-5d1a-11eb-86ff-111b0b7e962e.png)


---
## 주요 기능
- 회원 가입 및 로그인
- 팀 관리
- 팀원 관리
- 프로젝트 관리
- 프로젝트 토픽, 일정 관리

