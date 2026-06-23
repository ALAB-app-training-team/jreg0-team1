function ReservationList() {
    return (
        <>
            <div className="mx-auto flex max-w-4xl flex-col gap-8 p-4">
                <h1 className="text-4xl font-bold">予約確認</h1>
                <div className="bg-primary/5 flex gap-6 rounded-3xl p-2">
                    <div className="flex w-full items-center">
                        <div className="flex w-full items-center justify-center gap-2 rounded-3xl bg-white p-1">
                            <span className="material-symbols-outlined">
                                calendar_today
                            </span>
                            有効（ハード）
                        </div>
                        <div className="flex w-full items-center justify-center gap-2 rounded-3xl bg-white p-1">
                            <span className="material-symbols-outlined">
                                group
                            </span>
                            過去（ハード）
                        </div>
                        <div className="flex w-full items-center justify-center gap-2 rounded-3xl bg-white p-1">
                            <span className="material-symbols-outlined">
                                confirmation_number
                            </span>
                            キャンセル（ハード）
                        </div>
                    </div>
                </div>
                <div className="border-primary/20 flex flex-col gap-4 rounded-2xl border p-8">
                    <div className="flex-col">
                        <div className="flex">
                            <div className="flex grow-2 items-center gap-2 font-semibold">
                                <span className="material-symbols-outlined">
                                    confirmation_number
                                </span>
                                <h3>1号 - はやぶさ</h3>
                            </div>
                            <div className="bg-primary right-0 flex items-center justify-center rounded-xl px-3 text-sm text-white">
                                有効
                            </div>
                        </div>
                        <div className="flex py-2">
                            <h5>東京 → 上野</h5>
                        </div>
                    </div>
                    <div className="flex">
                        <div className="flex w-full flex-col">
                            <h5>出発</h5>
                            <h3 className="font-semibold">
                                2026年6月10日 10:00
                            </h3>
                        </div>
                        <div className="flex w-full flex-col">
                            <h5>ホーム</h5>
                            <h3 className="font-semibold">番線</h3>
                        </div>
                    </div>
                    <div className="flex">
                        <div className="flex flex-col gap-2 self-start">
                            <h5>座席</h5>
                            <div className="border-primary/20 flex flex-none rounded-2xl border p-2">
                                号車
                            </div>
                        </div>
                    </div>
                    <button className="contained_btn right-0 flex items-center justify-center gap-2">
                        <span className="material-symbols-outlined">
                            qr_code_2
                        </span>
                        チケットを表示
                    </button>
                </div>
            </div>
        </>
    );
}

export default ReservationList;
