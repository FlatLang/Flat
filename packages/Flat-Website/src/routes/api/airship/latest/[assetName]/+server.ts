import { AirshipController, flat_server_Request, FlatShort, FlatString, HashMap } from '$lib/server/server';

interface Proxy {
  headers: {[key: string]: string};
  statusCode: number;
  body: any;
  setHeader(key: FlatString, value: FlatString): void;
  status(statusCode: number): void;
  send(body: FlatString): void;
  redirect1(url: FlatString): void;
  end(): void;
}

/** @type {import('./$types').RequestHandler} */
export async function GET({ url, params }: { url: any; params: any }): Promise<Response> {
  const flatRequest = flat_server_Request.construct();
  flatRequest.query = HashMap.jsObjectToFlatHashMap(Object.fromEntries(url.searchParams));
  flatRequest.params = HashMap.jsObjectToFlatHashMap(params);

  const airship = AirshipController.construct();

  const responseProxy: Proxy = {
    headers: {},
    statusCode: 500,
    body: null,
    setHeader(key: FlatString, value: FlatString): void {
      this.headers[key.chars.data] = value.chars.data;
    },
    status(statusCode: number): void {
      this.statusCode = statusCode;
    },
    send(body: FlatString): void {
      this.body = body.chars.data;
    },
    redirect1(url: FlatString): void {
      this.body = 'Redirect';
      this.headers.Location = url.chars.data;
      this.statusCode = 302;
    },
    end(): void {},
  };

  await airship.getLatestAsset(flatRequest, responseProxy);

  return new Response(responseProxy.body, {
    status: responseProxy.statusCode,
    headers: responseProxy.headers,
  });
}
