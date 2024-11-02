// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if(deleteButton){
    deleteButton.addEventListener('click',event=>{
        let id=document.getElementById('article-id').value;
        fetch(`/api/articles/${id}`,{
            method:'DELETE'
        })
            .then(()=>{
                alert('삭제가 완료되었습니다.');
                location.replace('/articles');
            });
    });
}

// 수정 기능
// 1) id가 modify-btn 인 엘리먼트 조회
const modifyButton = document.getElementById('modify-btn');

if(modifyButton){
    // 2) 클릭 이벤트가 감지되면 수정 API 요청
    modifyButton.addEventListener('click',event=>{
        let params= new URLSearchParams(location.search); // URL의 쿼리문자열 부분(?로 시작하는 부분) 반환
        let id =params.get('id');

        fetch(`/api/articles/${id}`,{
            method:'PUT',
            headers: { // 요청을 보낼때는 header에 요청 형식을 지정
                "Content-Type": "application/json",
            },
            body : JSON.stringify({ // body에 HTML에 입력한 데이터를 JSON 형식으로 바꿔 보낸다.
                title: document.getElementById('title').value,
                content:document.getElementById('content').value
            })
        })
        .then(()=>{ // 요청 완료되면 then() 메소드로 마무리
            alert('수정이 완료되었습니다.');
            location.replace(`/articles/${id}`);
        });
    });
}

// 등록 기능
// 1) id가 create-btn 인 엘리먼트
const createButton = document.getElementById("create-btn");
if(createButton){
    // 2) 클릭 이벤트가 감지되면 생성 API 요청
    createButton.addEventListener("click",(event)=>{
        fetch("/api/articles",{
            method:"POST",
            headers:{
                "Content-Type":"application/json",
            },
            body:JSON.stringify({
                title: document.getElementById("title").value,
                content: document.getElementById("content").value,
            }),
        }).then(()=>{
            alert("등록이 완료되었습니다.");
            location.replace("/articles");
        });
    });
}