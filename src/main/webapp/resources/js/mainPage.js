
  $(function() {
    queryList.populate();

    allCurrencyOptions = document.getElementById("fromCurrency").innerHTML;

    $("#fromAmount").on("input",function () {
        makeConversion();
    });

    $( "#fromCurrency" ).selectmenu({
        change: function( event, data ) {
            var otherSelectId = "toCurrency";
            if( $("#"+otherSelectId).val() == data.item.value ) {
                selectDefaultOption(otherSelectId,initialTo);
            }
            makeConversion();
        }
     });

    $( "#toCurrency" ).selectmenu({
       change: function( event, data ) {
           var otherSelectId = "fromCurrency";
           if( $("#"+otherSelectId).val() == data.item.value ) {
                selectDefaultOption(otherSelectId,initialFrom);
           }
         makeConversion();
       }
     });

     $('#save').click(function () {
        console.debug("Going to send save request.");
        $.blockUI({
            theme: true,
            message: '<div class="text-center"><span class="glyphicon glyphicon-save" aria-hidden="true"></span>'
            + ' Saving query to server...</div>' }
        );
        var query = serializeQuery();
        $.ajax({
            type: "POST",
            url: "/query",
            data: JSON.stringify(query),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
            })
            .done( function(data){
                queryList.add(data,true);
            })
            .fail( function(errMsg) {
                console.log("Failed save request: "+errMsg);
            })
            .always( function() {
                $.unblockUI();
            });
     });

     $("#ratesDate").datepicker({
        minDate: new Date(1999, 1 - 1, 1),
        maxDate: 0,
        dateFormat: "yy-mm-dd",
        onSelect: refreshRates
     });
     $("#ratesDate").val("(now)")

     $("#fromAmount").val(initialValue);
     $("#fromCurrency").val(initialFrom);
     $("#fromCurrency").selectmenu("refresh");
     $("#toCurrency").val(initialTo);
     $("#toCurrency").selectmenu("refresh");
     makeConversion();


    /*** FUNCTIONS ***/

     function makeConversion() {
        var amount = $('#fromAmount').val();
        var fromCurrency = $('#fromCurrency').val();
        var toCurrency = $( "#toCurrency" ).val();
        var conversionResult = fx.convert(amount, {from: fromCurrency, to: toCurrency });
        $('#result').text( conversionResult );
     }

    function refreshRates(dateText) {
        if($(this).datepicker( "getDate" )) {
            var dateMillis = $(this).datepicker( "getDate" ).getTime();
            console.debug("Date refreshed to " + dateText + " : " + dateMillis);

            $.blockUI({
                theme: true,
                message: '<div class="text-center"><span class="glyphicon glyphicon-callendar" aria-hidden="true"></span>'
                + ' Requesting rates to server...</div>' }
            );
            $.get( "/rates", { date: dateText } )
                .done(function( data ) {
                    console.debug( "Received rates for " + data.timestamp );
                    fx.base = data.base;
                    fx.rates = data.rates;
                    makeConversion();
                })
                .fail( function(errMsg) {
                                console.log("Failed rates request: "+errMsg);
                            })
                .always( function() {
                                $.unblockUI();
                });
       }

    }

/* Select defaultOption. If it is already selected, select the first option of the select element.
   If that one too, is already selected, select the second option of the select element. */
     function selectDefaultOption(selectId,defaultOption) {
        var newOption = defaultOption;
        if( defaultOption == $("#"+selectId).val()) {
            newOption = $("#"+selectId + " option:first").val();
            if( newOption == $("#"+selectId).val()) {
               newOption = $("#"+selectId + " option:nth-child(2)").val();
            }
        }
        $("#"+selectId).val(newOption);
        $("#"+selectId).selectmenu("refresh");
     }

    function serializeQuery() {
        var queryElements=$("input,select");

        var jsonObject = {};

        $.each( queryElements,function() {
            jsonObject[this.name] = this.value; }
        );

        jsonObject["result"] = $("#result").text();

        var pickerDate = $("#ratesDate").datepicker( "getDate" );
        if(!pickerDate) {
            pickerDate = new Date();
        }
        jsonObject["ratesTimestamp"] = pickerDate.getTime();

        console.debug("Serialized query: " + JSON.stringify(jsonObject));

        return jsonObject;
    }
});


var queryList = {
    maxElements: 10,
    clear: function() {
        $("#queries").empty();
    },
    add: function(query,atTop) {
        console.debug("Adding query " + query.id + " to list.");
        var creationTime = new Date(query.creationTimestamp)
            .customFormat( "#DD#/#MM#/#YYYY# #hhh#:#mm#:#ss#" );
        var ratesTime = new Date(query.ratesTimestamp)
            .customFormat( "#DD#/#MM#/#YYYY#" );

        if( $("#queries li").length == queryList.maxElements ) {
            queryList.removeLast();
        }

        var $queryElement = $("<li id='query"+query.id+"'>"+ "<div class='text-center'>" + query.amount + " "
        + "<strong>"+query.fromCurrency+"</strong>" + " = " + query.result + " "
        + "<strong>"+query.toCurrency+"</strong>" + "</div>"
        + "<div class='text-center'>"
        + "<span class='glyphicon glyphicon-calendar' aria-hidden='true'></span>" + ratesTime + "&nbsp;&nbsp;&nbsp;"
        + "<span class='glyphicon glyphicon-save' aria-hidden='true'></span>" + creationTime + "</div>"
        +"</li>");

        if(atTop) {
            $queryElement.prependTo("#queries");
        } else {
            $queryElement.appendTo("#queries");
        }
    },
    removeLast: function() {
        $("#queries li:last-child").remove();
    },
    populate: function() {
        $.get("/query").done( function(data) {
            queryList.clear();
            console.debug("Received " + data.length + " queries");
            $.each(data, function() {
                queryList.add(this);
            })
        })
    }
};
