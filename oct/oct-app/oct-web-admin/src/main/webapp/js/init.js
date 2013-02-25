$(document).ready(function(){
	$("input[rel=date]").datepicker({
		inline: true,
		changeMonth: true,
		changeYear: true,
		dateFormat: "dd/mm/yy",
		yearRange: "2011:c"
	});

	if ($("#langChange")) {
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
				name: _select.attr("name")
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
		
		if (__code != "" && __default == "selected") {
			_select.createInput();
			$("#" + ADDITIONAL_FIELD_ID).val(__code);
		}
	}
});