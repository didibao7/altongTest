# altong publishing : sub-tree
해당 깃 저장소는 altong_2021 저장소에 사용되는 디자인 퍼블리싱 디렉토리입니다.

## altong publishing 설명(개발)
> 용도 : altong_2021 내부에서 sub-tree 를 사용하여 xxx/pub/ 폴더를 사용합니다.
> 외부 브랜치 : altong_2021 - 작업 브랜치는 master,
> 현재 저장소 브랜치는 main 입니다.

> 주의사항 : 개발자 PC 이클립스에서 해당 sub-tree 수정 시, 이곳에 반영되지 않습니다.
> (2021년 1월 기준) 이클립스에서 기능을 지원하지 않기 때문에 다음 방법을 사용합니다.
### 개발 로컬 저장소에 서브트리 관리하기
```sh
1. 서브트리로 사용할 원격 저장소 추가 (git-bash)
# git remote add <원격 저장소의 이름> <원격 저장소의 주소> ⮐
$ git remote add altong_2021_publishing git@github.com:askePhoenix/altong_2021_publishing.git 
$ git remote 
 - origin
 - altong_2021_publishing

2. 서브트리를 원격에서 내려받기(pull)
#git subtree pull --prefix <클론할 폴더> <원격 저장소의 이름> <브랜치 이름> ⮐
$ git subtree pull --prefix altong2021/src/main/webapp/pub altong_2021_publishing main 

3. 서브트리를 원격에 올리기(push)
#git subtree push --prefix <클론할 폴더> <원격 저장소의 이름> <브랜치 이름> ⮐
$ git subtree push --prefix altong2021/src/main/webapp/pub altong_2021_publishing main 


```

> 이하 사이트를 참고하였습니다.
> https://blog.rhostem.com/posts/2020-01-03-code-sharing-with-git-subtree
>


###  Git-SCM(git bash) 설치 (window)

Git-SCM 을 설치합니다. ( https://git-scm.com/ )
설치된 git-bash 를 실행합니다.

## Git Bash 사용법
```sh
$ pwd ⮐
실행결과 : /c/altong/a/b/c (현재 위치)
$ ls ⮐              #현재 위치에 있는 파일,폴더 리스트 보기
실행결과 : d/ 
$ cd d ⮐            #디렉토리(폴더) 이동하기(상대참조)
실행결과 : 없음(이동됨)
$ pwd ⮐             #현재 위치 출력
실행결과 : /c/altong/a/b/c/d (현재 위치)
$ cd /c/altong ⮐    #디렉토리(폴더) 이동하기(절대참조)
실행결과 : /c/altong/ (현재 위치)

각 주요 명령어(검색해서 사용하면됩니다)
# mkdir : 폴더 만들기
# rm : 파일( -r 옵션 사용시 디렉토리도 포함 )
# vim 또는 nano : 텍스트 편집기(복잡함)
# clear : 화면 글씨 지우기(청소하기)
# ls : 리스트 보기
# cd : 디렉토리 이동하기( ~ : 홈 위치, /c/ C 드라이브, /d/ D 드라이브 )
# alias : 별칭 주기
```

## publishing 작업
(ssh-rsa_key 는 이미 등록했습니다)
처음 저장소를 다운받을때만 쓰면 됩니다.
( git 명령 실수로 데이터가 소실됬으면 reflog로 head 찾고 reset 으로 복구됩니다. reset으로 다 날려도 살릴 수 있습니다 **.git** 만 안지우면 됩니다 )

***중요 : .gitignore 파일은 수정하면 안됩니다. /html/ 안에 작업한건 안올라갑니다.***

### 1. 깃허브 레파지토리(저장소) 클론(복사) 하기 
```sh
$ cd (작업공간) -> pwd          #(작업 위치 확인)
$ git init                      #작업공간 초기화
$ git remote add origin git@github.com:askePhoenix/altong_2021_publishing.git
$ git pull origin main
```
### 2. 퍼블리싱 깃 원격 저장소에 적용하기
```sh
$ git add .
$ git commit -m "(간단한 설명)"
$ git push origin main
```
### 3. 업데이트된 정보 최신화하기
```sh
$ git pull origin main
```

