function findItems(roadAddress){
    $.ajax({
        type: "GET",
        url: "/api/apt/items?road_address="+roadAddress,
        headers: {"content-type": "application/json"},
        dataType: "json",
    })
        .done(function (response) {
            console.log(response);
            if(!response.length){
                document.body.innerHTML = '<h6>등록된 매물이 없습니다</h6>';
            }
            else{
                makeCardElement(response, false);
            }
        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            alert("실패 : "+jqXHR.responseText);
        })
}

function findMyItems(){
    $.ajax({
        type: "GET",
        url: "/api/apt/myitems",
        headers: {"content-type": "application/json", 'Authorization':'Bearer '+getCookie("ATK").substring(4)},
        dataType: "json",
    })
        .done(function (response) {
            console.log(response);
            if(!response.length){
                document.body.innerHTML = '<h6>등록된 매물이 없습니다</h6>';
            }
            else{
                makeCardElement(response, true);
            }
        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            alert("실패 : "+jqXHR.responseText);
        })
}

function edit_item(item_id){
    confirm("이 글을 수정하시겠습니까?");
    location.href = "/item/edit/"+item_id;
}

function delete_item(item_id){
    console.log(item_id);
    confirm("이 글을 삭제하시겠습니까?");
    $.ajax({
        type: "DELETE",
        url: "/api/apt/item/"+item_id,
        headers: {"content-type": "application/json", 'Authorization':'Bearer '+getCookie("ATK").substring(4)},
        dataType: "text",
    })
        .done(function (response) {

            window.location.href = '/apt';
        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            alert("실패 : "+jqXHR.responseText);
        })
}

function makeCardElement(response, del_edit_btn){// response[ item[photo[],photo[]..  ],item[photo[]..], item[..]]
    var containerDiv = document.createElement("div");
    containerDiv.className = "container";
    // var rowDiv = document.createElement("div");
    // rowDiv.className="row";

    response.forEach((obj, idx) => {
        var cardDiv = document.createElement("div");
        cardDiv.classList.add('card');
        // cardDiv.style.width = "30rem";
        // cardDiv.style.margin = "5px";

        cardDiv.appendChild(makeCarouselElement(idx, obj.s3_files));

        var cardHaeder = document.createElement("div");
        cardHaeder.className = "card-header";
        cardHaeder.innerText = obj.title;
        var cardBody = document.createElement("div");
        cardBody.className = "card-body";
        cardBody.innerHTML = ` <h6 class="card-subtitle mb-2 text-body-secondary">${obj.created_date}</h6>\n` +
            `    <p>${obj.username}님 작성</p>\n`+
            `    <h5 class="card-title">매매|전/월세 : ${obj.dp_amount}</h5>\n`+
            `    <p>계약날짜 : ${obj.contract_date}</p>\n`+
            `    <p>${obj.phone_number}</p>\n`+
            `    <p>${obj.dp_area}</p>`+
            `    <p>${obj.dong}(동)</p>`+
            `    <p>${obj.floor}층</p>`+
            `    <p>${obj.build_year}년도 건설</p>`+
            `    <p class="card-text">상세설명 : ${obj.text}</p>\n` +
            '    <div id="mark"><i onclick="bookmark('+obj.id+')" class="bi bi-bookmark-plus"></i></div>';

        if(del_edit_btn==true){
            var delbtn = document.createElement("button");
            delbtn.className = "btn btn-secondary";
            delbtn.type = "button";
            delbtn.innerText = "삭제";
            delbtn.onclick = function (){
                delete_item(obj.id);
            }
            var editbtn = document.createElement("button");
            editbtn.className = "btn btn-secondary";
            editbtn.type = "button";
            editbtn.innerText = "수정";
            editbtn.onclick = function (){
                edit_item(obj.id);
            }
            cardBody.appendChild(editbtn);
            cardBody.appendChild(delbtn);
        }

        cardDiv.appendChild(cardHaeder);
        cardDiv.appendChild(cardBody);
        containerDiv.appendChild(cardDiv);
    });

    // containerDiv.appendChild(rowDiv);
    document.body.appendChild(containerDiv);
}
function makeCarouselElement(idx, images){// carousel이 여러개일 경우 carousel마다 id를 다르게 줘야함
    // Create main carousel element
    const carouselDiv = document.createElement('div');
    carouselDiv.classList.add('carousel', 'slide');
    carouselDiv.id = 'carouselExampleIndicators'+idx;

    // Create carousel indicators
    const indicatorsDiv = document.createElement('div');
    indicatorsDiv.classList.add('carousel-indicators');

    for (let i = 0; i < images.length; i++) {
        const indicatorButton = document.createElement('button');
        indicatorButton.type = 'button';
        indicatorButton.dataset.bsTarget = '#carouselExampleIndicators'+idx;
        indicatorButton.dataset.bsSlideTo = i;
        indicatorButton.setAttribute('aria-label', `Slide ${i + 1}`);

        if (i === 0) {
            indicatorButton.classList.add('active');
            indicatorButton.setAttribute('aria-current', 'true');
        }

        indicatorsDiv.appendChild(indicatorButton);
    }

    // Create carousel inner container
    const innerDiv = document.createElement('div');
    innerDiv.classList.add('carousel-inner');

    for (let i = 0; i < images.length; i++) {
        const carouselItemDiv = document.createElement('div');
        carouselItemDiv.classList.add('carousel-item');

        if (i === 0) {
            carouselItemDiv.classList.add('active');
        }

        const img = document.createElement('img');
        img.src = images[i].s3_url;
        img.classList.add('d-block', 'w-100');
        img.alt = `Image ${i + 1}`;

        carouselItemDiv.appendChild(img);
        innerDiv.appendChild(carouselItemDiv);
    }

    // create prev,next button
    var prevBtn = document.createElement("button");
    prevBtn.type = "button";
    prevBtn.className = "carousel-control-prev";
    prevBtn.dataset.bsTarget = "#carouselExampleIndicators"+idx;
    prevBtn.dataset.bsSlide = "prev";
    prevBtn.innerHTML = '<span className="carousel-control-prev-icon" aria-hidden="true"></span>'+
        '<span className="visually-hidden">Previous</span>';
    var nextBtn = document.createElement("button");
    nextBtn.type = "button";
    nextBtn.className = "carousel-control-next";
    nextBtn.dataset.bsTarget = "#carouselExampleIndicators"+idx;
    nextBtn.dataset.bsSlide = "next";
    nextBtn.innerHTML = '<span className="carousel-control-next-icon" aria-hidden="true"></span>'+
        '<span className="visually-hidden">Next</span>';

    // Append elements to the main container
    carouselDiv.appendChild(indicatorsDiv);
    carouselDiv.appendChild(innerDiv);
    carouselDiv.appendChild(prevBtn);
    carouselDiv.appendChild(nextBtn);

    // Append the main container to the document
    //     document.body.appendChild(carouselDiv);
    return carouselDiv;
}

function bookmark(item_id){
    $.ajax({
        type: "POST",
        url: "/api/bookmark/"+item_id,
        headers: {"content-type": "application/json", 'Authorization':'Bearer '+getCookie("ATK").substring(4)},
        dataType: "text",
    })
        .done(function (response) {
            var empty_mark = document.getElementById("mark");
            empty_mark.innerHTML = '<i class="bi bi-bookmark-check-fill"></i>';

            alert(response);
        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            alert("실패 : "+jqXHR.responseText);
        })
}