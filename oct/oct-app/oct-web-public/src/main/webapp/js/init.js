$(document).ready(function(){
	
	_datepicker = $.datepicker.regional[_langOCT];
	if(!_datepicker){
		_datepicker = $.datepicker.regional[''];
	} 
	_datepicker.inline = true;
	_datepicker.changeMonth = true;
	_datepicker.changeYear = true;
	_datepicker.dateFormat = "dd/mm/yy";
	_datepicker.yearRange = "1900:2010";
	_datepicker.defaultDate = "-16y";
	_datepicker.showMonthAfterYear = false;
	$("input[class=date]").datepicker(_datepicker);

	if ($("#langChange")) {
		$("#langChange").show();
		$("#langChange").change(function(evt) {
			var _lang = $("#langChange option:selected").val();
			if (_lang && _lang != "-1") {
				window.location = "?lang=" + _lang;
			}
		});
	}

	if ($("#countryCode")) {
		$("#countryCode").change(function(evt) {
			$("#submitBtn").click();
		});
		
		$("#submitBtn").hide();
	}
	
	$("form").each(function() {
		var _form = $(this);
		
		_form.find("input[type='submit']").click(function(evt) {
			var _hidden =  $('<input/>', {
				type: "hidden",
				name: $(this).attr("name")
			});
			_hidden.appendTo(_form); 
		});
		
		_form.submit(function(evt) {
			 _form.find("input[type='submit']").attr("disabled", "true");
		});
	});
	
	if ($("#countrySelect").length > 0) {
		var ADDITIONAL_FIELD_ID = "_additional_input";
		var BR_ID = "_br";

        var _select = $("#countrySelect");
        
		_select.createInput = function() {
			var _input =  $("<input/>", {
				id: ADDITIONAL_FIELD_ID,
				type: "text",
				name: _select.attr("name"),
				keydown: function(evt) {
					if (evt.keyCode == 13) {
						onEnterPressed(evt);
					}		
				} 
			});
			_input.insertAfter(_select);
			
			var _br = $("<br />", {
				id: BR_ID
			});
			_br.insertAfter(_select);
			
			_select.attr("name", "_oldselect");
		}
		
		_select.removeInput = function() {
			if ($("#" + ADDITIONAL_FIELD_ID)) {
				_select.attr("name", $("#" + ADDITIONAL_FIELD_ID).attr("name"));
			
				$("#" + ADDITIONAL_FIELD_ID).remove();
				$("#" + BR_ID).remove();						
			}
		}
		
		_select.change(function(){
			var _value = _select.val();
			if (_value == "other") {
				_select.createInput();
			} else {
				_select.removeInput();
			}
		});
		
		if (__code != "" && __default == "selected=&#034;selected&#034;") {
			_select.createInput();
			$("#" + ADDITIONAL_FIELD_ID).val(__code);
		}
	}
	
	$("form input").keydown(function(evt) {
		if (evt.keyCode == 13) {
			onEnterPressed(evt);
		}		
	});
	
	function onEnterPressed(evt) {
		evt.preventDefault();
		evt.stopPropagation();
		
		$("input[name='_finish']").click();
	}	
});