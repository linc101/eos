$(function(){
	$('.time.start').datePicker({followOffset : [0, 24]});
	$('.time.end').datePicker({followOffset : [0, 24]});
	$('.subButton').on('click',function(e){
		var name = $('.name').val();
		var serial = $('.workSerial').val();
		var start = $('.start').val();
		var end = $('.end').val();
		var type = $('.workType').val().trim();

		start = start.replace(/-/g, '/');
		var startDate = new Date(start).getTime();
		end = start.replace(/-/g, '/');
		var endDate = new Date(end).getTime();

		if(serial.trim() ==''){
			$('.workSerial').css({"border-color":"#f00"});
			return false;
		}
		if(name.trim() ==''){
			$('.name').css({"border-color":"#f00"});
			return false;
		}
		if(start.trim() ==''){
			$('.start').css({"border-color":"#f00"});
			return false;
		}
		if(end.trim() ==''){
			$('.end').css({"border-color":"#f00"});
			return false;
		}
		console.log("Type:" + type)
		if(type == "FTE" || type == "fte"){
			type = 1;
		}else if(type == "PTE" || type == "pte"){
			type = 2;
		}
		console.log("endTime:" + end);
		console.log("startTime:" + start);

		$(this).addClass('loading');
		$(this).val('');
		$.post('/SV/saveStaff',{
			staffName:name,
			staffId:serial,
			type:type,
			startTime :startDate,
			endTime:endDate
		},function(data,status){
			console.log(data);
			console.log(status);
			if(status == 'success'){
				$('.subBlock').html('提交成功');
			}
		});

		return false;
	});
	$('input').on('focus',function(e){
		$(this).removeAttr('style');
	});
});