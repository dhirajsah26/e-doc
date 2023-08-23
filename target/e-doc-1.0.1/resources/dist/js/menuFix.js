function menuFix() {
    //scroll sidebar based to make the content of dropdown visible
    var mainHeaderHeight = $('.main-header').height();

    $('.treeview a').click(function () {
        if (!$(this).closest('li').hasClass('menu-open')) {
            var removableHeight = 0;
            if ($(this).closest('li').siblings('li.menu-open').find('>.treeview-menu').length) {
                var selfIndex = $(this).closest('li').index();
                var openedLiIndex = $(this).closest('li').siblings('li.menu-open').index();
                if (openedLiIndex < selfIndex) {
                    removableHeight = $(this).closest('li').siblings('li.menu-open').find('>.treeview-menu').outerHeight();
                }
            }
            var sidebarViewTop = $('.sidebar').scrollTop();
            var sidebarHeight = $('.sidebar').outerHeight();
            var sidebarViewBottom = sidebarViewTop + sidebarHeight;
            var linkTop = $(this).offset().top - removableHeight - $(window).scrollTop() - mainHeaderHeight + sidebarViewTop;

            var treeViewMenuTop = linkTop + $(this).outerHeight();
            var treeViewMenu = $(this).siblings('.treeview-menu');
            var treeViewMenuHeight = treeViewMenu.outerHeight();
            var treeViewMenuBottom = treeViewMenuTop + treeViewMenuHeight;

            if (treeViewMenuBottom > sidebarViewBottom) {
                $('.sidebar').animate({scrollTop: treeViewMenuTop - (sidebarHeight - treeViewMenuHeight)}, 0);
            }
            if (treeViewMenuTop < sidebarViewTop) {
                $('.sidebar').animate({scrollTop: linkTop}, 0);
            }
        }
    });

    //scroll to active link on page load
    if ($('.sidebar-menu li.active').length) {
        var selector = $('.sidebar-menu li.active');
        var searchBarView = $('.sidebar-menu li.active.dashboard')[0] ? 32 : 0;

        if ($('.sidebar-menu>li.active>ul').length) {
            selector = selector.parents('.sidebar-menu').find('>li.active');
        }

        var scrollTop = selector.offset().top - $(window).scrollTop() - mainHeaderHeight - searchBarView;
        $('.sidebar').animate({scrollTop: scrollTop}, 50);
    }
}