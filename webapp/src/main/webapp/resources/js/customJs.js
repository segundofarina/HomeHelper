function generateStars() {
    $('.dyn-stars').each(function(){
        var elem = $(this);
        var rating = elem.data('rating');
        fillStars(elem, rating);
    });
}

function fillStars(container, rating) {
    var done = 0;

    for(var i = 1; i <= 5; i++) {
        if(rating < (i - 0.25) ) {
            //veo si es media
            if(!done && rating >= (i-0.75) ) {
                //es media
                container.append('<i class="fa fa-star-half-o"></i>');
            } else {
                //agrego vacia
                container.append('<i class="fa fa-star-o"></i>');
            }
            done = 1;
        } else {
            //agrego una entera
            container.append('<i class="fa fa-star"></i>');
        }
    }
}