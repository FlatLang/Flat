#ifndef NOVA_NATIVE_GC
#define NOVA_NATIVE_GC

#include <MacroLib.h>

#ifdef USE_GC
#include <gc.h>
#endif

void nova_gc_init();
long_long nova_gc_getFreeBytes();
long_long nova_gc_getTotalBytes();
long_long nova_gc_getHeapSize();
long_long nova_gc_getBytesSinceGC();
void nova_gc_collect();
void nova_gc_enableIncremental();
void nova_gc_dump();

#endif