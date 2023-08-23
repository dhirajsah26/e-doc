var dwidth = dheight = 176;	
var total_index = $('.sidebar li').length;
var charLimit = 1;
	
$(document).ready(function(){
	$('.sidebar-toggler').click(function(){
		$('.sidebar').toggleClass('visible');
	});
	
	$('.sidebar li').each(function(i,input){
		var title = $(input).find('span').text();
		var form = $(input).find('a').attr('href');
		$(form).prepend('<h3 style="display:none">'+title+'</h3>');
	});

	$('.sidebar a').click(function(e){
		e.preventDefault();
		var targetIndex = $(this).parent().index();
		changePage(targetIndex);
	});

	$('.btn-prev').click(function(){
		prevPage();
	});

	$('input').iCheck({
		checkboxClass: 'icheckbox_square-red',
		radioClass: 'iradio_square-red',
		increaseArea: '20%'
	});

	$('input').on('ifChanged', function() {
		var attr = $(this).attr('data-target-input');
		if (typeof attr != typeof undefined) {
			var target = $('input[data-parent='+attr+']');
			target.prop('disabled', function(idx, oldProp) {
				if (oldProp!=='true') {
					target.val('');
				}
				return !oldProp;
			});
		}
	});

	$('input[name="anySiblingStudyingHere"]').on('ifChecked', function(event) {
		$('.sibling_studying_in_kws_value .form-control').each(function(){
			var target = $(this);
			target.prop('disabled', function(idx, oldProp) {
				if (oldProp!=='true') {
					target.val('');
				}
				return !oldProp;
			});
		});
	});

	$("input.imgInput").change(function() {
		var img = $('.'+$(this).attr('imgTarget'));

		if (file = this.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$(img).attr("src",e.target.result);
				updateImageDimension(img);
			}

			reader.readAsDataURL(this.files[0]);
		}
	});

	$('#photo_upload img').each(function(i,img){
			updateImageDimension(img);	
	});


    $("input[name='fullNameInEnglish']").keydown(function(e) {
        var keys = [8, 9, /*16, 17, 18,*/ 19, 20, 27, 32, 33, 34, 35, 36, 37, 38, 39, 40, 45, 46, 144, 145];
        if (e.which == 8 && this.value.length == 0) {
            $(this).prev('.form-control').focus();
            $(this).prev('.form-control').val('');
        } else if ($.inArray(e.which, keys) >= 0) {
            return true;
        } else if (this.value.length >= charLimit) {
            $(this).next('.form-control').focus();
            return false;
        } else if (e.which <= 64 || e.which >= 91) {
            return false;
        }
    }).keyup (function (e) {
        if (e.which !=8 && e.which!=37 && e.which!=39 && this.value.length >= charLimit) {
            $(this).next('.form-control').focus();
            return false;
        }
    });
});

function nextPage(){
	var index = $('.sidebar li.active').index();
	var targetIndex = index+1;	
	changePage(targetIndex);
}

function prevPage(){
	var index = $('.sidebar li.active').index();
	var targetIndex = index-1;	
	changePage(targetIndex);
}

function changePage(targetIndex){
	var title = $('.sidebar li').eq(targetIndex).find('span').text();
    var form_id = $('.sidebar li.active>a').attr('href');
	$('.sidebar li').removeClass('active');
    $('.content .form-block').addClass('d-none');

    $('.sidebar li').eq(targetIndex).addClass('active');
    $('.content .form-block').eq(targetIndex).removeClass('d-none');

    $('.content-header h2').text(title);

    if(targetIndex == 0){
    	$('.btn-prev,.btn-submit').addClass('d-none');
    	$('.btn-next').removeClass('d-none');
    }else if(targetIndex == total_index-1){
       	$('.btn-prev,.btn-submit').removeClass('d-none');
    	$('.btn-next').addClass('d-none');
    }else{
    	$('.btn-submit').addClass('d-none');
       	$('.btn-next,.btn-prev').removeClass('d-none');
    }
    $(window).scrollTop(0);
}

function updateImageDimension(img){
	var image = new Image();
	image.onload = function(){
		var fwidth = fheight = dwidth;
		var width = image.width;
		var height = image.height;
		var aRatio = width/height;
		var marginLeft = marginTop = 0;

		if(width>height){
			fwidth = aRatio*dheight;
			marginLeft = (fwidth-dwidth)/2;
		}else{
			fheight = (1/aRatio)*dwidth;
			marginTop = (fheight-dheight)/2;
		}

		$(img).css({'width':fwidth,'height':fheight,'margin-left':-marginLeft+'px','margin-top':-marginTop+'px'});
	};
	image.src = $(img).attr('src');
}