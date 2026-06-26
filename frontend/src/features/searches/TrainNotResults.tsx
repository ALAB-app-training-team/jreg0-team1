type TrainNoResultsProps = {
    handleNextDateSearch:
        | React.MouseEventHandler<HTMLButtonElement>
        | undefined;
    isActiveNextDaySearchButton: boolean;
};

function TrainNoResults({
    handleNextDateSearch,
    isActiveNextDaySearchButton,
}: TrainNoResultsProps) {
    return (
        <div className="border-primary/20 flex flex-col items-center gap-4 rounded-2xl border p-8">
            <div className="bg-primary/10 flex aspect-square items-center justify-center rounded-full p-4">
                <span className="material-symbols-outlined">error</span>
            </div>
            <h3 className="font-semibold">指定日時の列車はありません</h3>
            <h5>
                お選びいただいた日時以降の列車が見つかりませんでした。条件を変更するか、翌日の列車を検索してください。
            </h5>
            <button
                className="contained_btn flex items-center gap-2"
                onClick={handleNextDateSearch}
                disabled={!isActiveNextDaySearchButton}
            >
                <span className="material-symbols-outlined">trending_up</span>
                翌日の列車を検索
            </button>
        </div>
    );
}

export default TrainNoResults;
