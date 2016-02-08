<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">	
<title>Tree View </title>
<content tag="pageName">projectPage</content>

<style>
.treeview, .treeview ul {
	list-style-type: none;
}

.treeview li {
	text-indent: 1%;
	margin-top:0.2em;
	padding:0.15em 0 0.5em 1.5em;
	line-height: 22px;
}

.treeview li.contains-items {
	padding:0.15em 0 0.5em 1.5em;
	background-repeat:no-repeat;
	background-image: url('../images/treeView/arrow-left.png');
}

.treeview li.items-expanded {
	background-image: url('../images/treeView/arrow-down.png');
}

.treeview>li:hover {
	cursor: pointer;
}
</style>

</head>
<body>
<div class="navigation">			
		<h3>Tree View</h3>
</div>
<div class="treeview-container">
	<ul id="dataTree" id="treeView"></ul>
</div>



<script>
(function ($){
	// doesn't work without jquery
	if (!$) return;
	// treeView
	function treeView($me) {
		// add treeview class name if not present
		$me.addClass('treeview');
		// collapsable elements i.e. the li with a ul in it
		var $collapse = $me.find('li>ul').parent();
		// generate tree from data
		function generateTree(data, $root, options) {
			// create a node from a node object
			
			function createNode(nObj, $target) {
				var li = $('<li>').appendTo($target);
				
				//li = li.innerHTML().append($('a'));
				// node icons require using a span element
				var useSpan = options.useSpan || options.imageList.length > 0;
				if (useSpan) {
					li.append($('<span>').text(nObj.label));
				} else {
					
					//li.append($('<a href="${createLink(controller:'project', action:'loadParentProject')}?code="+nObj.label+"})"').text(nObj.label));
					li.append($('<a href=\" ${createLink(controller:'project', action:'loadParentProject')}?code='+nObj.label+'\">').text(nObj.label));
					li.append($('</a>'));
					//li.text(nObj.label);
				}
				if(options.imageList.length > 0){
					// the image
					var image = 'url('+options.imageList[nObj.imageIndex]+')';
					// requires using span
					var $span = li.find('span');
					// indicates that it has a node image
					$span.addClass('has-node-icon');
					$span.css('background-image', image);
				}
				if (nObj.children != undefined && nObj.children.length > 0) {
					var innerList = $('<ul>').appendTo(li);
					for (var i = 0; i < nObj.children.length; i++) {
						var child = nObj.children[i];
						createNode(child, innerList);
					};
				}
				
				return li;
			}
			for (var i = 0; i < data.length; i++) {
				createNode(data[i], $root);
			}
		}

		return {
			//initialize control
			init: function (data) {
				// handle undefined error
				data = data || { };

				// default optoins
				var defaults = {
					model: null, // treeview data model
					useSpan: false, // use <span> to build model
					imageList: [], // add icons to nodes
					// ajax: null, TODO: load data using ajax
					expanded: false // the tree is expanded
				};
				// configuration
				var options = { };
				
				if (typeof data.concat != 'undefined') {
					// concat is an array method, thus checks if data is array
					// typeof array returns object otherwise
					defaults.model = data;
					// data has model only, which is transferred to defaults.model
					// prevents wrong merge in $.extend
					data = null;
				}
				// merge options
				options = $.extend(defaults, data);

				if (options.model != null) {
					// generate the tree
					generateTree(options.model, $me, options);
					// re assign var value for new dom structure
					$collapse = $me.find('li>ul').parent();
				}
				// all the collapsable items which have something
				$collapse.addClass('contains-items');
				// user config
				if (options.expanded){
					$collapse.addClass('items-expanded')
				} else {
					$me.find('ul').css('display', 'none');
				}
				// expand items which have something
				$me.find('.contains-items').on('click', function (event) {
					if ($(event.target).hasClass('contains-items')){
						// expand icon
						$(this).toggleClass('items-expanded');
						// the inner list
						var $a = $(this).find('>ul');
						// slide effect
						$a.slideToggle();
						// stop propagation of inner elements
						event.stopPropagation();
					}
				});
			},
			// expand all items
			expandAll: function() {
				var items = $me.find('.contains-items');
				items.find('ul').slideDown();
				items.addClass('items-expanded');
			},
			// collapse all items
			collapseAll: function() {
				var items = $me.find('.contains-items');
				items.find('ul').slideUp();
				items.removeClass('items-expanded');
			}
		}
	}
	// treeView jQuery plugin
	$.fn.treeView = function(options) {
		// if it's a function arguments
		var args = (arguments.length > 1) ? Array.prototype.slice.call(arguments, 1) : undefined;
		// all the elements by selector
		return this.each(function () {
			var instance = new treeView($(this));
			if ( instance[options] ) {
				// has requested method
				return instance[options](args);
			} else {
				// no method requested, so initialize
				instance.init(options);
			}
		});
	}

})(window.jQuery);

</script>
<script>
$('#samplesTree li li').on('click', function () {
	var className = $(this).html();
	$('.content').children().css('display', 'none');
	$('.'+className).css('display', 'block');
});

// intialise all the treeView(s)
$('.treeview').treeView();
// handle expand and collapse buttons
$('.btn-expand').click(function () {
	$('.treeview').treeView('expandAll');
});
$('.btn-collapse').click(function () {
	$('.treeview').treeView('collapseAll');
})
// load the data tree
var $dataTree = $('#dataTree');
var $iconTree = $('.icon-tree');

var myString = "${treeView}";

var find = '&quot;';
var re = new RegExp(find, 'g');
myString = myString.replace(re, '\"');

find = '&#39;';
re = new RegExp(find, 'g');
myString = myString.replace(re, '\'');

find = '&lt;';
re = new RegExp(find, 'g');
myString = myString.replace(re, '<');

find = '&gt;';
re = new RegExp(find, 'g');
myString = myString.replace(re, '>');


var model = JSON.parse(myString);

var iModel = $.extend({}, model);
var imgs = ['css/icons/samples/folder.png', 'css/icons/samples/file.png'];

var iconModel = [
    {
        "label": "item1",
        "children": [],
        "imageIndex": 0
    },
    {
        "label": "item2",
        "children": [
            {
                "label": "subItem",
                "imageIndex": 1
            },
            {
                "label": "another subItem",
                "imageIndex": 1
            },
            {
                "label": "last subItem",
                "imageIndex": 1
            }
        ],
        "imageIndex": 0
    },
    {
        "label": "item3",
        "children": [
            {
                "label": "Hello",
                "imageIndex": 1
            },
            {
                "label": "Inner List",
                "imageIndex": 0,
                "children": [
                    {
                        "label": "innerItem1",
                        "imageIndex": 1
                    },
                    {
                        "label": "innerItem2",
                        "imageIndex": 1
                    }
                ]
            },
            {
                "label": "Bye",
                "imageIndex": 1
            }
        ],
        "imageIndex": 0
    }
];
// create icon tree
$iconTree.treeView({ 
	model: iconModel,
	imageList: imgs
});

$dataTree.treeView(model);

$('.treview-dotted-lines').treeView('expandAll');

</script>

</body>
</html>
