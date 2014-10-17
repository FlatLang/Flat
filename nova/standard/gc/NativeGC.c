#include "NativeGC.h"

void nova_gc_init()
{
#ifdef USE_GC
	GC_INIT();
#else

#endif
}

long_long nova_gc_getFreeBytes()
{
#ifdef USE_GC
	return GC_get_free_bytes();
#else
	return 0;
#endif
}

long_long nova_gc_getTotalBytes()
{
#ifdef USE_GC
	return GC_get_total_bytes();
#else
	return 0;
#endif
}

long_long nova_gc_getHeapSize()
{
#ifdef USE_GC
	return GC_get_heap_size();
#else
	return 0;
#endif
}

long_long nova_gc_getBytesSinceGC()
{
#ifdef USE_GC
	return GC_get_bytes_since_gc();
#else
	return 0;
#endif
}

void nova_gc_collect()
{
#ifdef USE_GC
	GC_gcollect();
#else
	
#endif
}

void nova_gc_enableIncremental()
{
#ifdef USE_GC
	GC_enable_incremental();
#else

#endif
}

void nova_gc_dump()
{
#ifdef USE_GC
	GC_dump();
#else

#endif
}