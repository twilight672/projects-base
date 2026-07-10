jQuery(function($) {
    $('#contactform').validate({
        rules: {
            username: {
                required: true,
                minlength: 2
            },
            email: {
                required: true,
                email: true
            }
        },
        messages: {
            username: {
                required: "Please enter your name",
                minlength: "Your name must consist of at least 2 characters"
            },
            email: {
                required: "Please enter your email"
            }
        }
    });
    $('#newsletterform').validate({
        rules: {
            email: {
                required: true,
                email: true
            }
        },
        submitHandler: function(form) {
            $(form).ajaxSubmit({
                type:"POST",
                data: $(form).serialize(),
                url:"external/form/newsletter-form.php",
                success: function() {
                      $('#success').fadeIn();
            $('#newsletterform').each(function(){this.reset();});
                },
                error: function() {
                    $('#newsletterform').fadeTo( "slow", 1, function() {
                        $('#error').fadeIn();
                    });
                }
            });
        }
    });
});