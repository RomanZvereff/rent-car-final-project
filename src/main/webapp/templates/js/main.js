window.addEventListener('DOMContentLoaded', event => {

    // Navbar shrink function
    var navbarShrink = function () {
        const navbarCollapsible = document.body.querySelector('#mainNav');
        if (!navbarCollapsible) {
            return;
        }
        if (window.scrollY === 0) {
            navbarCollapsible.classList.remove('navbar-shrink')
        } else {
            navbarCollapsible.classList.add('navbar-shrink')
        }

    };

    // Shrink the navbar
    navbarShrink();

    // Shrink the navbar when page is scrolled
    document.addEventListener('scroll', navbarShrink);

    // Activate Bootstrap scrollspy on the main nav element
    const mainNav = document.body.querySelector('#mainNav');
    if (mainNav) {
        new bootstrap.ScrollSpy(document.body, {
            target: '#mainNav',
            offset: 74,
        });
    };
});

let today = new Date();
let dd1 = today.getDate();
let mm1 = today.getMonth() + 1; //January is 0!
const yyyy1 = today.getFullYear();
if(dd1 < 10){
    dd1 = '0' + dd1;
}
if(mm1 < 10){
    mm1 = '0' + mm1;
}
let curDay = yyyy1 + '-' + mm1 + '-' + dd1;
document.getElementById("start").setAttribute("min", curDay);
document.getElementById("start").setAttribute("value", curDay);

let nextDay = today.setDate(today.getDate() + 1);
let dd2 = today.getDate();
let mm2 = today.getMonth() + 1; //January is 0!
const yyyy2 = today.getFullYear();
if(dd2 < 10){
    dd2 = '0' + dd2;
}
if(mm2 < 10){
    mm2 = '0' + mm2;
}
nextDay = yyyy2 + '-' + mm2 + '-' + dd2;
document.getElementById("end").setAttribute("min", nextDay);
document.getElementById("end").setAttribute("value", nextDay);




(function ($) {
    "use strict";


    /*==================================================================
    [ Focus input ]*/
    // $('.input100').each(function(){
    //     $(this).on('blur', function(){
    //         if($(this).val().trim() != "") {
    //             $(this).addClass('has-val');
    //         }
    //         else {
    //             $(this).removeClass('has-val');
    //         }
    //     })
    // })
  
  
    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit',function(){
        var check = true;

        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) == false){
                showValidate(input[i]);
                check=false;
            }
        }

        return check;
    });


    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });

    function validate (input) {
        if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        }
        else {
            if($(input).val().trim() == ''){
                return false;
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }
    
    /*==================================================================
    [ Show pass ]*/
    var showPass = 0;
    $('.btn-show-pass').on('click', function(){
        if(showPass == 0) {
            $(this).next('input').attr('type','text');
            $(this).addClass('active');
            showPass = 1;
        }
        else {
            $(this).next('input').attr('type','password');
            $(this).removeClass('active');
            showPass = 0;
        }
        
    });


})(jQuery);