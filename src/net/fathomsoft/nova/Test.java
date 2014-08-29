package net.fathomsoft.nova;

import static java.lang.Math.sin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.StringUtils;

public class Test
{
	/**
	 * Replace a directory name in the given path String.<br>
	 * For example:
	 * <blockquote><pre>
	 * String path = "/path/to/changethis/directory/here";
	 * 
	 * String value = replaceDirectoryName(path, "changethis", "newDir");</pre></blockquote>
	 * The above value String would have the contents of:
	 * "/path/to/newDir/directory/here"
	 * 
	 * @param path The path to use as the source String to change a
	 * 		directory name in.
	 * @param oldName The old directory name to change.
	 * @param newName The new directory name to update the old one as.
	 * @return The newly generated String path.
	 */
	private static String replaceDirectoryName(String path, String oldName, String newName)
	{
		if (path.indexOf(oldName + '/') >= 0)
		{
			return path.replace(oldName + '/', newName + '/');
		}
		else if (path.indexOf('/' + oldName) >= 0)
		{
			return path.replace('/' + oldName, '/' + newName);
		}
		else if (path.equals(oldName))
		{
			return newName;
		}
		
		return path;
	}
	
	private static double sin2(double d)
	{
		return sin(d);
	}
	
	public static void d(Object o, String s)
	{
		
	}
	
	public static void d(String s, Object o)
	{
		System.out.println("String");
	}
	
	public static void d(CharSequence s, Object o)
	{
		System.out.println("Charseq");
	}
	
	public static void d(CharSequence s)
	{
		d(s, (Object)s);
	}
	
	public static void d(String s)
	{
		System.out.println("Str");
		
		d(s, (Object)s);
	}
	
	public static void main(String args[])
	{
		System.out.println(StringUtils.removeContentsWithin("This<E  >d is a test", "<", ">"));
		
		System.exit(0);
		
		String s = "ADF";
		
		CharSequence c = (CharSequence)s;
		
		d(c);
		
		System.exit(0);
//		String array[] = new String[9999999];
		long start = System.currentTimeMillis();
//		
//		for (int i = 0; i < 9999999; i++)
//		{
//			array[i] = Integer.toString(i);
//		}
		
		for (int i = 0; i < 999999; i++)
		{
			Integer.toString(i);
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println(end - start);
		
		System.exit(0);
		try
		{
			try
			{
				throw new RuntimeException();
			}
			catch (IllegalArgumentException e)
			{
				System.out.println("illegal arg");
			}
			finally
			{
				System.out.println("in finally...");
			}
		}
		catch (RuntimeException e)
		{
			System.out.println("cauht runtime excep");
		}
		
		int f[] = new int[] {45, 12 , 22 , 4};
		
		int f2[] = new int[f.length - 1];
		
		System.arraycopy(f, 1, f2, 1, f2.length - 1);
		
		for (int a : f)
		{
			System.out.print(a + ", ");
		}
		System.out.println();
		for (int a : f2)
		{
			System.out.print(a + ", ");
		}
		System.out.println();
		
//		long start = System.currentTimeMillis();
//		for (int i = 0; i < 99999999; i++)
//		{
//			sin2(i);
//		}
//		long end = System.currentTimeMillis();
//		
//		System.out.println(end - start);
		
//		FPattern pattern = new FPattern("([;} \n\r]`s*)((`2startps?`identifier+`2endps?`s*)+[*&]*`s*[\\(]`~*[\\)]`s*[;])()");
//		FMatcher matcher = new FMatcher("/*This is comment stuff*/ \n public void test \tis a test(these, are, parameters);", pattern);
//		
////		TierPattern pattern = new TierPattern("([;} \n\r]`s*)(([a-zA-Z0-9_]+`s*)+[*&]*`s*[\\(]`~*[\\)]`s*[;])()");
////		TierMatcher matcher = new TierMatcher("/*This is comment stuff*/ \n public void test \tis a test(these, are, parameters);", pattern);
////		
//		int  count    = 0;
//		
//		long findTime = System.currentTimeMillis();
//		
//		while (matcher.find())
//		{
//			count++;
//			System.out.println(matcher.start() + ": " + matcher.group());
//		}
//		
//		findTime = System.currentTimeMillis() - findTime;
//		
//		System.out.println(findTime);
		
		
		
//		TierPattern s2 = new TierPattern("(\"is d\")");
//		TierMatcher s  = new TierMatcher("this is a test asdf", s2);
//		
//		while (s.find())
//		{
//			System.out.println(s.start());
//		}
//		System.out.println("done");
		
//		long createTime = System.currentTimeMillis();
//		
//		FPattern pattern = new FPattern("([;} \n\r]`s*)((`2startps?`identifier+`2endps?`s*)+[*&]?`s*[\\(]`~*[\\)]`s*[;])()");//"([;} \n\r]`s*)((`2startps?`identifier+`2endps?`s*)+[*&]?`s*[\\(]`~*[\\)]`s*[;])()");
//		FMatcher matcher = new FMatcher("typedef unsigned int size_t;\ntypedef short unsigned int wchar_t;typedef short unsigned int wint_t;typedef __builtin_va_list __gnuc_va_list;typedef struct _iobuf{ char* _ptr; int _cnt; char* _base; int _flag; int _file; int _charbuf; int _bufsiz; char* _tmpfname;} FILE;extern __attribute__ ((__dllimport__)) FILE _iob[]; "
//				+ "FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fopen (const char*, const char*);FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) freopen (const char*, const char*, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fflush (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fclose (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) remove (const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) rename (const char*, const char*); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) tmpfile (void); char* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) tmpnam (char*); char* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _tempnam (const char*, const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _rmtmp(void); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _unlink (const char*); char* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) tempnam (const char*, const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) rmtmp(void); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) unlink (const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) setvbuf (FILE*, char*, int, size_t); void __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) setbuf (FILE*, char*);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_fprintf(FILE*, const char*, ...);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_printf(const char*, ...);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_sprintf(char*, const char*, ...);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_snprintf(char*, size_t, const char*, ...);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_vfprintf(FILE*, const char*, __gnuc_va_list);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_vprintf(const char*, __gnuc_va_list);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_vsprintf(char*, const char*, __gnuc_va_list);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_vsnprintf(char*, size_t, const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fprintf (FILE*, const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) printf (const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) sprintf (char*, const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vfprintf (FILE*, const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vprintf (const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vsprintf (char*, const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __msvcrt_fprintf(FILE*, const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __msvcrt_printf(const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __msvcrt_sprintf(char*, const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __msvcrt_vfprintf(FILE*, const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __msvcrt_vprintf(const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __msvcrt_vsprintf(char*, const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _snprintf (char*, size_t, const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _vsnprintf (char*, size_t, const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _vscprintf (const char*, __gnuc_va_list);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) snprintf (char *, size_t, const char *, ...);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vsnprintf (char *, size_t, const char *, __gnuc_va_list);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vscanf (const char * __restrict__, __gnuc_va_list);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vfscanf (FILE * __restrict__, const char * __restrict__,       __gnuc_va_list);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vsscanf (const char * __restrict__,       const char * __restrict__, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fscanf (FILE*, const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) scanf (const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) sscanf (const char*, const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fgetc (FILE*); char* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fgets (char*, int, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fputc (int, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fputs (const char*, FILE*); char* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) gets (char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) puts (const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) ungetc (int, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _filbuf (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _flsbuf (int, FILE*);extern __inline__ int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) getc (FILE* __F){  return (--__F->_cnt >= 0)    ? (int) (unsigned char) *__F->_ptr++    : _filbuf (__F);}extern __inline__ int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) putc (int __c, FILE* __F){  return (--__F->_cnt >= 0)    ? (int) (unsigned char) (*__F->_ptr++ = (char)__c)    : _flsbuf (__c, __F);}extern __inline__ int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) getchar (void){  return (--(&_iob[0])->_cnt >= 0)    ? (int) (unsigned char) *(&_iob[0])->_ptr++    : _filbuf ((&_iob[0]));}extern __inline__ int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) putchar(int __c){  return (--(&_iob[1])->_cnt >= 0)    ? (int) (unsigned char) (*(&_iob[1])->_ptr++ = (char)__c)    : _flsbuf (__c, (&_iob[1]));} size_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fread (void*, size_t, size_t, FILE*); size_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fwrite (const void*, size_t, size_t, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fseek (FILE*, long, int); long __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) ftell (FILE*); void __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) rewind (FILE*);typedef long long fpos_t; int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fgetpos (FILE*, fpos_t*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fsetpos (FILE*, const fpos_t*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) feof (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) ferror (FILE*); void __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) clearerr (FILE*); void __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) perror (const char*); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _popen (const char*, const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _pclose (FILE*); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) popen (const char*, const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) pclose (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _flushall (void); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fgetchar (void); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fputchar (int); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fdopen (int, const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fileno (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fcloseall (void); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fsopen (const char*, const char*, int); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _getmaxstdio (void); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _setmaxstdio (int); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fgetchar (void); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fputchar (int); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fdopen (int, const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fileno (FILE*);typedef int ptrdiff_t;typedef long __time32_t;typedef long long __time64_t;typedef __time32_t time_t;typedef long _off_t;typedef _off_t off_t;typedef unsigned int _dev_t;typedef _dev_t dev_t;typedef short _ino_t;typedef _ino_t ino_t;typedef int _pid_t;typedef _pid_t pid_t;typedef unsigned short _mode_t;typedef _mode_t mode_t;typedef int _sigset_t;typedef _sigset_t sigset_t;typedef int _ssize_t;typedef _ssize_t ssize_t;typedef long long fpos64_t;typedef long long off64_t;typedef unsigned int useconds_t;extern __inline__ FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fopen64 (const char* filename, const char* mode){  return fopen (filename, mode);}int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fseeko64 (FILE*, off64_t, int);extern __inline__ off64_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) ftello64 (FILE * stream){  fpos_t pos;  if (fgetpos(stream, &pos))    return -1LL;  else   return ((off64_t) pos);} int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fwprintf (FILE*, const wchar_t*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) wprintf (const wchar_t*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _snwprintf (wchar_t*, size_t, const wchar_t*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vfwprintf (FILE*, const wchar_t*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vwprintf (const wchar_t*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _vsnwprintf (wchar_t*, size_t, const wchar_t*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _vscwprintf (const wchar_t*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fwscanf (FILE*, const wchar_t*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) wscanf (const wchar_t*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) swscanf (const wchar_t*, const wchar_t*, ...); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fgetwc (FILE*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fputwc (wchar_t, FILE*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) ungetwc (wchar_t, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) swprintf (wchar_t*, const wchar_t*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vswprintf (wchar_t*, const wchar_t*, __gnuc_va_list); wchar_t* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fgetws (wchar_t*, int, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fputws (const wchar_t*, FILE*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) getwc (FILE*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) getwchar (void); wchar_t* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _getws (wchar_t*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) putwc (wint_t, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _putws (const wchar_t*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) putwchar (wint_t); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wfdopen(int, const wchar_t *); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wfopen (const wchar_t*, const wchar_t*); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wfreopen (const wchar_t*, const wchar_t*, FILE*); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wfsopen (const wchar_t*, const wchar_t*, int); wchar_t* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wtmpnam (wchar_t*); wchar_t* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wtempnam (const wchar_t*, const wchar_t*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wrename (const wchar_t*, const wchar_t*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wremove (const wchar_t*); void __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wperror (const wchar_t*); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wpopen (const wchar_t*, const wchar_t*);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) snwprintf (wchar_t* s, size_t n, const wchar_t* format, ...);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vsnwprintf (wchar_t* s, size_t n, const wchar_t* format, __gnuc_va_list arg);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vwscanf (const wchar_t * __restrict__, __gnuc_va_list);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vfwscanf (FILE * __restrict__,         const wchar_t * __restrict__, __gnuc_va_list);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vswscanf (const wchar_t * __restrict__,         const wchar_t * __restrict__, __gnuc_va_list); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) wpopen (const wchar_t*, const wchar_t*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fgetwchar (void); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fputwchar (wint_t); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _getw (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _putw (int, FILE*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fgetwchar (void); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fputwchar (wint_t); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) getw (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) putw (int, FILE*);", pattern);
//		
//		createTime = System.currentTimeMillis() - createTime;
//		
//		int  count    = 0;
//		
//		long findTime = System.currentTimeMillis();
//		
//		while (matcher.find())
//		{
//			count++;
//			System.out.println(matcher.start() + ": " + matcher.group());
//		}
//		
//		findTime = System.currentTimeMillis() - findTime;
//		
//		System.out.println("create: " + createTime);
//		System.out.println("run: " + findTime + " " + count);
		
		
		long createTime = System.currentTimeMillis();
		
		Pattern p = Patterns.UNARY_ARITH_OPERATORS;//Pattern.compile("(?<=([;}]\\s?))(([a-zA-Z0-9_\\*\\&]+\\s)+\\(^[\\)]*\\);)");
		Matcher m = p.matcher("test  -- ");
		//"typedef unsigned int size_t;\ntypedef short unsigned int wchar_t;typedef short unsigned int wint_t;typedef __builtin_va_list __gnuc_va_list;typedef struct _iobuf{ char* _ptr; int _cnt; char* _base; int _flag; int _file; int _charbuf; int _bufsiz; char* _tmpfname;} FILE;extern __attribute__ ((__dllimport__)) FILE _iob[]; FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fopen (const char*, const char*);FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) freopen (const char*, const char*, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fflush (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fclose (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) remove (const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) rename (const char*, const char*); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) tmpfile (void); char* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) tmpnam (char*); char* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _tempnam (const char*, const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _rmtmp(void); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _unlink (const char*); char* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) tempnam (const char*, const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) rmtmp(void); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) unlink (const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) setvbuf (FILE*, char*, int, size_t); void __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) setbuf (FILE*, char*);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_fprintf(FILE*, const char*, ...);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_printf(const char*, ...);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_sprintf(char*, const char*, ...);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_snprintf(char*, size_t, const char*, ...);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_vfprintf(FILE*, const char*, __gnuc_va_list);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_vprintf(const char*, __gnuc_va_list);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_vsprintf(char*, const char*, __gnuc_va_list);extern int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __mingw_vsnprintf(char*, size_t, const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fprintf (FILE*, const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) printf (const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) sprintf (char*, const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vfprintf (FILE*, const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vprintf (const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vsprintf (char*, const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __msvcrt_fprintf(FILE*, const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __msvcrt_printf(const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __msvcrt_sprintf(char*, const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __msvcrt_vfprintf(FILE*, const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __msvcrt_vprintf(const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) __msvcrt_vsprintf(char*, const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _snprintf (char*, size_t, const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _vsnprintf (char*, size_t, const char*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _vscprintf (const char*, __gnuc_va_list);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) snprintf (char *, size_t, const char *, ...);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vsnprintf (char *, size_t, const char *, __gnuc_va_list);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vscanf (const char * __restrict__, __gnuc_va_list);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vfscanf (FILE * __restrict__, const char * __restrict__,       __gnuc_va_list);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vsscanf (const char * __restrict__,       const char * __restrict__, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fscanf (FILE*, const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) scanf (const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) sscanf (const char*, const char*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fgetc (FILE*); char* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fgets (char*, int, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fputc (int, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fputs (const char*, FILE*); char* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) gets (char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) puts (const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) ungetc (int, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _filbuf (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _flsbuf (int, FILE*);extern __inline__ int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) getc (FILE* __F){  return (--__F->_cnt >= 0)    ? (int) (unsigned char) *__F->_ptr++    : _filbuf (__F);}extern __inline__ int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) putc (int __c, FILE* __F){  return (--__F->_cnt >= 0)    ? (int) (unsigned char) (*__F->_ptr++ = (char)__c)    : _flsbuf (__c, __F);}extern __inline__ int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) getchar (void){  return (--(&_iob[0])->_cnt >= 0)    ? (int) (unsigned char) *(&_iob[0])->_ptr++    : _filbuf ((&_iob[0]));}extern __inline__ int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) putchar(int __c){  return (--(&_iob[1])->_cnt >= 0)    ? (int) (unsigned char) (*(&_iob[1])->_ptr++ = (char)__c)    : _flsbuf (__c, (&_iob[1]));} size_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fread (void*, size_t, size_t, FILE*); size_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fwrite (const void*, size_t, size_t, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fseek (FILE*, long, int); long __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) ftell (FILE*); void __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) rewind (FILE*);typedef long long fpos_t; int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fgetpos (FILE*, fpos_t*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fsetpos (FILE*, const fpos_t*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) feof (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) ferror (FILE*); void __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) clearerr (FILE*); void __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) perror (const char*); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _popen (const char*, const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _pclose (FILE*); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) popen (const char*, const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) pclose (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _flushall (void); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fgetchar (void); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fputchar (int); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fdopen (int, const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fileno (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fcloseall (void); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fsopen (const char*, const char*, int); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _getmaxstdio (void); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _setmaxstdio (int); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fgetchar (void); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fputchar (int); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fdopen (int, const char*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fileno (FILE*);typedef int ptrdiff_t;typedef long __time32_t;typedef long long __time64_t;typedef __time32_t time_t;typedef long _off_t;typedef _off_t off_t;typedef unsigned int _dev_t;typedef _dev_t dev_t;typedef short _ino_t;typedef _ino_t ino_t;typedef int _pid_t;typedef _pid_t pid_t;typedef unsigned short _mode_t;typedef _mode_t mode_t;typedef int _sigset_t;typedef _sigset_t sigset_t;typedef int _ssize_t;typedef _ssize_t ssize_t;typedef long long fpos64_t;typedef long long off64_t;typedef unsigned int useconds_t;extern __inline__ FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fopen64 (const char* filename, const char* mode){  return fopen (filename, mode);}int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fseeko64 (FILE*, off64_t, int);extern __inline__ off64_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) ftello64 (FILE * stream){  fpos_t pos;  if (fgetpos(stream, &pos))    return -1LL;  else   return ((off64_t) pos);} int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fwprintf (FILE*, const wchar_t*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) wprintf (const wchar_t*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _snwprintf (wchar_t*, size_t, const wchar_t*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vfwprintf (FILE*, const wchar_t*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vwprintf (const wchar_t*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _vsnwprintf (wchar_t*, size_t, const wchar_t*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _vscwprintf (const wchar_t*, __gnuc_va_list); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fwscanf (FILE*, const wchar_t*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) wscanf (const wchar_t*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) swscanf (const wchar_t*, const wchar_t*, ...); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fgetwc (FILE*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fputwc (wchar_t, FILE*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) ungetwc (wchar_t, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) swprintf (wchar_t*, const wchar_t*, ...); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vswprintf (wchar_t*, const wchar_t*, __gnuc_va_list); wchar_t* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fgetws (wchar_t*, int, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fputws (const wchar_t*, FILE*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) getwc (FILE*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) getwchar (void); wchar_t* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _getws (wchar_t*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) putwc (wint_t, FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _putws (const wchar_t*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) putwchar (wint_t); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wfdopen(int, const wchar_t *); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wfopen (const wchar_t*, const wchar_t*); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wfreopen (const wchar_t*, const wchar_t*, FILE*); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wfsopen (const wchar_t*, const wchar_t*, int); wchar_t* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wtmpnam (wchar_t*); wchar_t* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wtempnam (const wchar_t*, const wchar_t*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wrename (const wchar_t*, const wchar_t*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wremove (const wchar_t*); void __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wperror (const wchar_t*); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _wpopen (const wchar_t*, const wchar_t*);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) snwprintf (wchar_t* s, size_t n, const wchar_t* format, ...);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vsnwprintf (wchar_t* s, size_t n, const wchar_t* format, __gnuc_va_list arg);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vwscanf (const wchar_t * __restrict__, __gnuc_va_list);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vfwscanf (FILE * __restrict__,         const wchar_t * __restrict__, __gnuc_va_list);int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) vswscanf (const wchar_t * __restrict__,         const wchar_t * __restrict__, __gnuc_va_list); FILE* __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) wpopen (const wchar_t*, const wchar_t*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fgetwchar (void); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _fputwchar (wint_t); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _getw (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) _putw (int, FILE*); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fgetwchar (void); wint_t __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) fputwchar (wint_t); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) getw (FILE*); int __attribute__((__cdecl__)) __attribute__ ((__nothrow__)) putw (int, FILE*);");
		
		createTime = System.currentTimeMillis() - createTime;
		
		int count = 0;
		
		long findTime = System.currentTimeMillis();
		
		while (m.find())
		{
			count++;
//			System.out.println(m.start());
			System.out.println(m.start() + ": " + m.group());
		}
		
		findTime = System.currentTimeMillis() - findTime;

		System.out.println("create: " + createTime);
		System.out.println("run: " + findTime + " " + count);
		
		System.out.println("Done");
	}
}