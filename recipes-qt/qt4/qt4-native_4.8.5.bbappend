TOBUILD_append = "\
  src/declarative \
  src/script \
  src/svg \
"

do_install_append() {
	ln -s -f libQtGui.so ${D}${libdir}/libQtGuiE.so
	ln -s -f libQtCore.so ${D}${libdir}/libQtCoreE.so
	ln -s -f qt4 ${D}${datadir}/qtopia
	ln -s -f qt4 ${D}${includedir}/qtopia
}
