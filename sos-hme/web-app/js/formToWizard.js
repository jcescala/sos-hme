/* Created by jankoatwarpspeed.com */
var $j = jQuery.noConflict();
(function($j) {
    $j.fn.formToWizard = function(options) {
        options = $j.extend({  
            submitButton: "" 
        }, options); 
        
        var element = this;

        var steps = $j(element).find("fieldset");
        var count = steps.size();
        var submmitButtonName = "#" + options.submitButton;
        $j(submmitButtonName).hide();

        // 2
        $j(element).before("<ul id='steps'></ul>");

        steps.each(function(i) {
            $j(this).wrap("<div id='step" + i + "'></div>");
            $j(this).append("<p id='step" + i + "commands'></p>");

            // 2
            var name = $j(this).find("legend").html();
            $j("#steps").append("<li id='stepDesc" + i + "'>Paso " + (i + 1) + "<span>" + name + "</span></li>");

            if (i == 0) {
                createNextButton(i);
                selectStep(i);
            }
            else if (i == count - 1) {
                $j("#step" + i).hide();
                createPrevButton(i);
            }
            else {
                $j("#step" + i).hide();
                createPrevButton(i);
                createNextButton(i);
            }
        });

        function createPrevButton(i) {
            var stepName = "step" + i;
            $j("#" + stepName + "commands").append("<a href='#' id='" + stepName + "Prev' class='prev'>< Anterior</a>");

            $j("#" + stepName + "Prev").bind("click", function(e) {
                $j("#" + stepName).hide();
                $j("#step" + (i - 1)).show();
                $j(submmitButtonName).hide();
                selectStep(i - 1);
            });
        }

        function createNextButton(i) {
            var stepName = "step" + i;
            $j("#" + stepName + "commands").append("<a href='#' id='" + stepName + "Next' class='next'>Siguiente ></a>");

            $j("#" + stepName + "Next").bind("click", function(e) {
                $j("#" + stepName).hide();
                $j("#step" + (i + 1)).show();
                if (i + 2 == count)
                    $j(submmitButtonName).show();
                selectStep(i + 1);
            });
        }

        function selectStep(i) {
            $j("#steps li").removeClass("current");
            $j("#stepDesc" + i).addClass("current");
        }

    }
})(jQuery); 


