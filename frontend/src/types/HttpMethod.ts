export const httpMethod = {
    POST: "POST", 
    PATCH: "PATCH",
    DELETE: "DELETE"
} as const;

export type HttpMethod = typeof httpMethod[keyof typeof httpMethod];

export const isHttpMethod = (value:string): value is HttpMethod => {
    return ["POST", "PATCH", "DELETE"].includes(value);
}
