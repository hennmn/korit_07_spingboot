package com.example.cardatabase.domain;

public record AccountCredentials(String username, String password) {}
// AppUser 필드에 있는 username, password 아님 (의미는 같지만 쓰임새가 다름)
// AccountCredentials는 record라서 단순히 로그인 시 클라이언트가 보내는 username / password를 담는 DTO 역할을 합니다.
// 즉 로그인 요청 시 JSON 같은 형태로 들어온 데이터를 받기 위한 그릇
/*
    Record는 데이터 운반체(Data Carrier) 역할을 하는 클래스를 간결하게 생성하기 위해 14에서 도입되었고, 16에서 정식 기능이 됐습니다.
    주로 DTO(Data Transfer Object)
 */


/*
    AccountCredentials -> 로그인 요청을 받을 때 사용(임시 객체, DB와 직접 연결 안 됨.)
    AppUser -> DB에 저장된 실제 사용자 정보
 */