import logo from '@/assets/logo.png';

function Header() {
    return (
        <header className="border-b-primary/20 flex h-24 items-center border-b">
            <img src={logo} alt="jreg0のロゴ" className="h-20" />
        </header>
    );
}

export default Header;
