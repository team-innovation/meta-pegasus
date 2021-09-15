do_install_append () {
    sed -i 's/set mouse=a/\"set mouse=a/' ${D}/${datadir}/${BPN}/vimrc
    sed -i 's/set backup.*\"/set nobackup         "do not/'  ${D}/${datadir}/${BPN}/vimrc
    sed -i '/set undofile/d'  ${D}/${datadir}/${BPN}/vimrc
    echo "" >> ${D}/${datadir}/${BPN}/vimrc
    echo "set background=dark" >> ${D}/${datadir}/${BPN}/vimrc
}

