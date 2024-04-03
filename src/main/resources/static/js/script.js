function toggleSidebar(){
var x = window.matchMedia("(max-width: 768px)")
x.addListener(toggleSidebar)
if (x.matches){
    if($(".sidebar").is(":visible")){

    $(".sidebar").css("display","none");
    $(".contents").css("margin-left","0%")

    }
    else{
    $(".sidebar").css("display","block");
        $(".contents").css("margin-left","35%")
    }
    }
    else{
        if($(".sidebar").is(":visible")){

        $(".sidebar").css("display","none");
        $(".contents").css("margin-left","4%")

        }
        else{
        $(".sidebar").css("display","block");
            $(".contents").css("margin-left","20%")
        }
        }

}

