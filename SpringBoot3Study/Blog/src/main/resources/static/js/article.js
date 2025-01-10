// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if(deleteButton){
    deleteButton.addEventListener('click',event =>{
        let id =document.getElementById('article-id').value;
        function success(){
            alert("삭제가 완료되었습니다.");
            location.replace("/articles");
        }
        function fail(){
            alert("삭제가 실패했습니다.");
            location.replace("/articles");
        }

        httpRequest("DELETE","/api/articles/"+id,null,success,fail);
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if(modifyButton){
    modifyButton.addEventListener('click', event=>{
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        body = JSON.stringify({
            title:document.getElementById("title").value,
            content:document.getElementById("content").value,
        });
        function success(){
            alert("수정 완료되었습니다.");
            location.replace("/articles/"+id);
        }
        function fail(){
            alert("수정 실패했습니다.");
            location.replace("/articles/"+id);
        }

        httpRequest("PUT","/api/articles/"+id,body,success,fail);
    });
}

// 등록 기능
// 1. id 가 create-btn인 엘리먼트
const createButton = document.getElementById("create-btn");
if(createButton){
    // 등록 버튼을 클릭하면 /api/aricles로 요청을 보냄
    createButton.addEventListener("click", (event) => {

        body = JSON.stringify({
           title: document.getElementById("title").value,
           content: document.getElementById("content").value
       });
        console.log("등록 요청 데이터:", body); // 요청 데이터 확인

        function success(){
           alert("등록 완료되었습니다.");
           location.replace("/articles");
       }
       function fail(){
           alert("등록 실패했습니다.");
           console.error("등록 실패"); // 실패 로그 추가
           location.replace("/articles");
       }

       httpRequest("POST","/api/articles",body,success,fail);
    });
}


// 쿠키를 가져오는 함수
function getCookie(key){
    var result = null;
    var cookie = document.cookie.split(";");
    cookie.some(function (item){
        item= item.replace(" ","");
        var dic=item.split("=");
        if(key ===dic[0]){
            result = dic[1];
            return true;
        }
    });
    return result;
}

// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    console.log(`HTTP 요청: ${method} ${url}`);
    console.log("요청 본문:", body);

    console.log("액세스 토큰:", localStorage.getItem("access_token"));
    console.log("리프레시 토큰:", getCookie("refresh_token"));

    fetch(url, {
        method: method,
        headers: { // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json',
        },
        body: body,
    }).then(response => {
        // 응답 상태 확인을 위한 로깅 추가
        console.log('Response status:', response.status);
        console.log('Response:', response); // 전체 응답 확인

        if (response.status === 200 || response.status === 201) {
            return success();
        }
        const refresh_token = getCookie('refresh_token');
        if (response.status === 401 && refresh_token) {
            console.warn("401 Unauthorized - 새 토큰 요청 중...");
            return fetch('/api/token', {
                method: 'POST',
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    refreshToken: getCookie('refresh_token'),
                }),
            })
                .then(res => {
                    if (res.ok) {
                        return res.json();
                    }
                    throw new Error("리프레시 토큰 요청 실패");
                })
                .then(result => { // 재발급이 성공하면 로컬 스토리지값을 새로운 액세스 토큰으로 교체
                    console.log("새 액세스 토큰:", result.accessToken);
                    localStorage.setItem("access_token", result.accessToken);
                    return httpRequest(method, url, body, success, fail);
                })
                .catch(error => {
                    console.error("토큰 갱신 실패:", error);
                    fail();
                });
        } else {
            console.error("요청 실패:", response.status, response.statusText);
            fail();
        }
    })
    .catch((error) => {
        console.error("HTTP 요청 에러:", error);
        fail();
    });
}