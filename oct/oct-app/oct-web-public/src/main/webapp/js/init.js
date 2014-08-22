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

	if (document.getElementById('langChange')) {
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
	
    if (document.getElementById('map')) {
    	$('#map').addClass("loading");
		$.ajax({
			url: 'mapdata.do',
            type: 'get',
            dataType: 'json',
            cache: false,
            async: true,
            success: function(data) {
            	$("#total").text(data.total);
            	
            	var _colors = [];
            	var _data = [];
            	for (var idx = 0; idx < data.countries.length; idx++) {
            		var _c = data.countries[idx];
            		var _cnt = parseInt(_c.count);
            		var _threshold = parseInt(_c.threshold);
            		_colors[_c.code] = _cnt == 0 ? "#FF614E" : (_cnt < _threshold ? '#51A1D1' : '#A1C14C');
            		_data[_c.code] = _c; 
            	}
            	
				jQuery('#map').vectorMap({ 
					map: 'europe_en',
					backgroundColor: '#FFFFFF',
					borderColor: '#111111',
					borderOpacity: 1,
					borderWidth: 1,
					color: '#CDB49F',
					enableZoom: true,
					hoverColor: '#C7E0F3',
					hoverOpacity: null,
					showTooltip: true,
					colors: _colors,
					onLabelShow: function(event, label, code) {
						var _c = _data[code];
						if (_c) {
							label.removeClass("hidden");
							label.html('<div class="label-container"><p class="country">' + _c.name + '</p>' + 
									data.messages.collected + ': ' + _c.count + '<br />' + 
									data.messages.threshold + ': ' + _c.threshold + "<br />" + 
									data.messages.percentage + ': ' + _c.percentage + '%</div>');
						} else {
							label.addClass("hidden");
						}
					},
					onRegionClick: function(event, label, code) {
						event.preventDefault();
					}
					
				});
				
				$('#map').removeClass("loading");
			},
            error: function(jqXHR, textStatus, errorThrown ) {
			} 
        });
	}
});
