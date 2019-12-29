var bmsUrl = "";
var timeout;
var existingTheaters = {};

Object.size = function(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
};

function fetchShows(successCallback) {
    $.get("rest/fetch?bmsUrl=" + bmsUrl, successCallback);
}

function getShowsString(theater) {
    var showsString = "";
    for(i = 0; i < theater.shows.length; i++) {
        showsString += theater.shows[i].time;

        if(i !== (theater.shows.length - 1)) {
            showsString += ", ";
        }
    }
    return showsString;
}

function populateTable(theaters) {
    var tableHead = $("#availableTable tbody");

    tableHead.empty();
    $.each(theaters, function(index, value) {

        var row = "<tr>" +
                        "<td>" + value.id + "</td>" +
                        "<td>" + value.name + "</td>" +
                        "<td>" + getShowsString(value) + "</td>" +
                  "</tr>";
       tableHead.append(row);
    });

    $("#total").html(Object.size(theaters));
    $("#lastRefreshed").html(new Date());
}

function notify(theaters) {
    $.each(!jQuery.isEmptyObject(existingTheaters) && theaters, function(index, value) {
        // publishing event only if no theater exists
        if( typeof existingTheaters[index] === 'undefined') {
            var options = {
                title : "Bookings added for New theater",
                options: {
                    body: value.name + "\n" + getShowsString(value),
                    lang: "en-US",
                    onClick: function() {
                        var win = window.open(bmsUrl, '_blank');
                        win.focus();
                    }
                }
            }
            $("#availableTable").easyNotify(options);
        }
    });
}

function populateAndNotify(response) {
    populateTable(response);
    notify(response);
    existingTheaters = response;
    timeout = setTimeout(function() {
        fetchShows(populateAndNotify);
    }, 30 * 1000);
}

function subscribeMovie() {
    bmsUrl = $("#bmsUrl").val();
    existingTheaters = {};

    if(typeof timeout !== 'undefined') {
        clearTimeout(timeout);
    }

    fetchShows(populateAndNotify);
}