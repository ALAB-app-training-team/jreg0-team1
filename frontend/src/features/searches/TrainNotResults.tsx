type TrainNoResultsProps = {
    handleNextDateSearch: React.MouseEventHandler<HTMLButtonElement> | undefined,
}

function TrainNoResults({
    handleNextDateSearch
}:TrainNoResultsProps){
    return (
    <div className="flex flex-col items-center border border-primary/20 rounded-2xl p-8 gap-4">
        <div className="rounded-full bg-primary/10 aspect-square flex items-center justify-center p-4">
            <span className="material-symbols-outlined">error</span>
        </div>
        <h3 className="font-semibold">指定日時の列車はありません</h3>
        <h5>お選びいただいた日時以降の列車が見つかりませんでした。条件を変更するか、翌日の列車を検索してください。</h5>
        <button className="contained_btn flex items-center gap-2"
        onClick={handleNextDateSearch}>
            <span className="material-symbols-outlined">trending_up</span>
            翌日の列車を検索
        </button>
    </div>
    );
}

export default TrainNoResults;
