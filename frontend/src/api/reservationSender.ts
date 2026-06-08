import type { ArgForSend } from "@/features/searches/types/Reservation";

const reservationSender =(url:string, {arg}: {arg: ArgForSend}): Promise<String> => {
    const data = fetch(url, {
        method: arg.method,
        headers: {
          "Content-Type": "application/json",
        },
        body: arg.body ? JSON.stringify(arg.body) : undefined,
    })
    .then(res => res.text())
    return data;
}

export default reservationSender;
