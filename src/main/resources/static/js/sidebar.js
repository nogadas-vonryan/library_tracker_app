const sidebarButton = document.querySelector('.sidebar-toggle');
const sidebarContainer = document.querySelector('.sidebar');

sidebarButton.onclick = function() {
    const className = 'translate-x-full';
    sidebarContainer.classList.add('transition-all');

    if(sidebarContainer.classList.contains(className))
        sidebarContainer.classList.remove(className);
    else
        sidebarContainer.classList.add(className);
}
